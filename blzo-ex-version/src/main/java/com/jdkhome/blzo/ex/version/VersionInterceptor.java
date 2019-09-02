package com.jdkhome.blzo.ex.version;

import com.jdkhome.blzo.ex.basic.constants.BasicSystemConstants;
import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.version.annotation.MinVersion;
import com.jdkhome.blzo.ex.version.constants.VersionConstants;
import com.jdkhome.blzo.ex.version.enums.VersionResponseError;
import com.jdkhome.blzo.ex.version.tools.VersionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * author link.ji
 * createTime 下午5:09 2018/6/19
 * <p>
 * 版本检查拦截器
 */
@Slf4j
@Component
public class VersionInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果未加@Version注解，不需要校验version
        MinVersion versionAnn = method.getAnnotation(MinVersion.class);
        if (versionAnn == null) {
            return true;
        }

        if (versionAnn.value().equals(VersionConstants.ANY_VERSION_CODE)
                && versionAnn.max().equals(VersionConstants.ANY_VERSION_CODE)) {
            // 0.0.0 默认
            return true;
        }

        //从header中得到token
        String version = request.getHeader(VersionConstants.VERSION);
        log.debug("版本校验 -> 请求version:{}", version);
        I18nEnums i18n = I18nEnums.getByCode(request.getHeader(BasicSystemConstants.i18n));
        //验证token, token存在则将userId注入到request属性
        if (VersionTools.checkVersion(version, versionAnn.value())) {

            if ((!versionAnn.max().equals(VersionConstants.ANY_VERSION_CODE))
                    && VersionTools.checkVersion(version, versionAnn.max())) {
                throw new ServiceException(VersionResponseError.API_DEPRECATED, i18n);
            }

            request.setAttribute(VersionConstants.VERSION, version);
            return true;
        } else {
            throw new ServiceException(VersionResponseError.VERSION_UPGRADE, i18n);
        }

    }


}
