package com.jdkhome.blzo.ex.authj.core;

import com.jdkhome.blzo.ex.authj.service.LogBasicService;
import com.jdkhome.blzo.ex.basic.aop.api.ApiRecordAspect;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.basic.tools.IpTools;
import com.jdkhome.blzo.ex.basic.tools.gson.PerfectGson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by jdk.
 */
@Component
public class AuthjInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    LogBasicService logBasicService;

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Authj authj = null;
        String uri = null;
        if (handler instanceof HandlerMethod) {

            HandlerMethod h = (HandlerMethod) handler;
            authj = h.getMethodAnnotation(Authj.class);
            // 没有注解
            if (authj == null) {
                return true;
            }

            uri = request.getServletPath();
            if (!authjManager.authentication(uri)) {
                // 没有权限

                if (uri.startsWith("/api")) {
                    // api 返回没有权限
                    ApiResponse resp = ApiResponse.error(BasicResponseError.NO_PERMISSION);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json");
                    PrintWriter pw = response.getWriter();
                    pw.write(PerfectGson.getGson().toJson(resp));
                    pw.flush();
                } else {
                    // page 跳转到错误页面
                    response.setCharacterEncoding("utf-8");
                    response.sendRedirect("/manage/login");
                }
                return false;
            }
        }

        if (authj != null && authj.auth()) {
            logBasicService.addLog(authjManager.getOrganizeId(),
                    authjManager.getUserId(),
                    authjManager.getUserName(),
                    uri, authj.value(),
                    String.format("query:%s \n body:%s",
                            ApiRecordAspect.getParamerStr(request), ApiRecordAspect.getBodyStr(request)),
                    IpTools.getIp(request));
        }
        return true;
    }

}
