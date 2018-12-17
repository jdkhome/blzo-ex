package com.jdkhome.blzo.ex.version.configuration;

import com.jdkhome.blzo.ex.version.VersionInterceptor;
import com.jdkhome.blzo.ex.version.VersionMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Component
public class VersionInterceptorConfig extends WebMvcConfigurerAdapter {


    @Autowired
    VersionInterceptor versionInterceptor;

    @Autowired
    VersionMethodArgumentResolver versionMethodArgumentResolver;

    /**
     * 添加登录校验拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册版本校验拦截器
        registry.addInterceptor(versionInterceptor);

        super.addInterceptors(registry);

    }

    /**
     * 添加version参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(versionMethodArgumentResolver);
    }

}
