package com.jdkhome.blzo.ex.basic.configuration;

import com.jdkhome.blzo.ex.basic.aop.api.ApiBeforeCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jdk on 2019/1/7.
 */
@Slf4j
@Configuration
public class ApiBeforeConfig {

    @Bean
    ApiBeforeCustom apiBeforeCustom() {
        return joinPoint -> log.debug("[apiBeforeCustom] -> nothing todo.");
    }

}
