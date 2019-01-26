package com.jdkhome.blzo.ex.risk;

import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.risk.annotation.Risk;
import com.jdkhome.blzo.ex.risk.constants.RiskRedisKeyCons;
import com.jdkhome.blzo.ex.risk.enums.RiskResponseError;
import com.jdkhome.blzo.ex.utils.tools.IpTools;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdk on 2019/1/23.
 */
@Slf4j
@Component
public class RiskChecker {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 将该check 加入到 ApiBeforeCustom 即可
     *
     * @param joinPoint
     */
    public void check(JoinPoint joinPoint) {

        // 获取Request信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = IpTools.getIp(request);

        // 获取Risk 注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Risk risk = method.getAnnotation(Risk.class);

        // 检查
        if (risk != null && risk.risk()) {
            String uri = request.getRequestURI();

            // 首先检查IP是否被控制
            if (stringRedisTemplate.hasKey(String.format(RiskRedisKeyCons.RISK_VALID_IP_URI, ip, uri))) {
                log.error("RISK -> 风控限制 IP :{} URI :{} ", ip, uri);
                throw new ServiceException(RiskResponseError.RISK);
            }

            // 记录风控
            stringRedisTemplate.opsForValue().set(String.format(RiskRedisKeyCons.RISK_RECORD_IP_URI_TIME, ip, uri, String.valueOf(new Date().getTime())), "1", risk.period(), TimeUnit.SECONDS);

            Set keys = stringRedisTemplate.keys(RiskRedisKeyCons.RISK_RECORD);

            if (risk.count() <= (CollectionUtils.isEmpty(keys) ? 0 : keys.size())) {
                stringRedisTemplate.opsForValue().set(String.format(RiskRedisKeyCons.RISK_VALID_IP_URI, ip, uri), String.valueOf(new Date().getTime()), risk.time(), TimeUnit.SECONDS);
                log.error("RISK -> 触发风控 IP :{} URI :{} time :{}", ip, uri, risk.time());
                throw new ServiceException(RiskResponseError.RISK);
            }

        }
    }
}


