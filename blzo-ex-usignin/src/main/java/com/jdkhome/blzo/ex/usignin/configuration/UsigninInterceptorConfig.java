package com.jdkhome.blzo.ex.usignin.configuration;

import com.jdkhome.blzo.ex.usignin.CurrentUserMethodArgumentResolver;
import com.jdkhome.blzo.ex.usignin.UserSignInInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Component
public class UsigninInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    UserSignInInterceptor userSiginInInterceptor;

    @Autowired
    CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;


    /**
     * 添加登录校验拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册app登录校验拦截器
        registry.addInterceptor(userSiginInInterceptor);

        super.addInterceptors(registry);

    }

    /**
     * 添加user参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }

}


