package com.jdkhome.blzo.ex.basic.configuration;

import com.jdkhome.blzo.ex.basic.i18n.I18nMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 注册国际化参数解析器
 */
@Component
public class I18nInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    I18nMethodArgumentResolver i18nMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(i18nMethodArgumentResolver);
    }

}


