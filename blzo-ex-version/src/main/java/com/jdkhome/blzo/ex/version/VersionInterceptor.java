package com.jdkhome.blzo.ex.version;

import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.utils.tools.gson.PerfectGson;
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
import java.io.PrintWriter;
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
            //logger.info("登录校验->不需要校验token");
            return true;
        }

        if (versionAnn.value().equals(VersionConstants.ANY_VERSION_CODE)) {
            return true;
        }

        //从header中得到token
        String version = request.getHeader(VersionConstants.VERSION);
        log.info("版本校验->请求version:{}", version);

        //验证token, token存在则将userId注入到request属性
        if (VersionTools.checkVersion(version, versionAnn.value())) {
            request.setAttribute(VersionConstants.VERSION, version);
            return true;
        } else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write(PerfectGson.getGson().toJson(ApiResponse.error(VersionResponseError.VERSION_UPGRADE)));
            pw.flush();
            return false;
        }
    }
}
