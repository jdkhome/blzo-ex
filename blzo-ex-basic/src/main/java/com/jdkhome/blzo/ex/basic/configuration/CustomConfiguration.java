package com.jdkhome.blzo.ex.basic.configuration;

import com.google.gson.Gson;
import com.jdkhome.blzo.ex.basic.tools.gson.PerfectGson;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jdk on 2019/1/7.
 * 自定义json格式化工具
 */
@Configuration
public class CustomConfiguration {


    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(createGsonHttpMessageConverter());
        return new HttpMessageConverters(true, messageConverters);
    }

    private GsonHttpMessageConverter createGsonHttpMessageConverter() {
        Gson gson = PerfectGson.getGson();
        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gson);
        return gsonConverter;
    }
}
