package com.jdkhome.blzo.ex.usignin;

import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.usignin.annotation.UserMaybeSignin;
import com.jdkhome.blzo.ex.usignin.annotation.UserSignin;
import com.jdkhome.blzo.ex.usignin.constants.UsigninConstants;
import com.jdkhome.blzo.ex.usignin.token.TokenManager;
import com.jdkhome.blzo.ex.basic.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by jdk on 17/9/6.
 * 登录校验拦截器，拦截请求方法进行登录校验(token校验)
 *
 * @CreatedBy jdk
 * @Date 17/9/6
 */
@Slf4j
@Component
public class UserSignInInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果未加@SignIn注解，不需要校验token
        if (method.getAnnotation(UserSignin.class) == null && method.getAnnotation(UserMaybeSignin.class) == null) {
            //logger.info("登录校验->不需要校验token");
            return true;
        }

        //从header中得到token
        String token = request.getHeader(UsigninConstants.TOKEN);
        log.info("用户登录校验->请求token:{}", token);


        //验证token, token存在则将userId注入到request属性
        if (tokenManager.checkToken(token)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(UsigninConstants.USER_ID, tokenManager.getUserId(token));

            //刷新token有效时间
            tokenManager.refreshTokenExpire(token);

            return true;
        } else {

            if (method.getAnnotation(UserMaybeSignin.class) != null) {
                return true;
            }

            // 如果验证token失败，直接返回错误
            if (!request.getServletPath().startsWith("/api/")) {
                // 重定向
                response.sendRedirect("/app/reLogin");
                return false;
            }

            log.info("用户登录校验->token验证失败，需要重新登录:{}", token);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write(PerfectGson.getGson().toJson(ApiResponse.error(BasicResponseError.NO_LOGIN)));
            pw.flush();
            return false;
        }
    }
}
