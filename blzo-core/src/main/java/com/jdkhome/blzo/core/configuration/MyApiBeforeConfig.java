package com.jdkhome.blzo.core.configuration;

import com.jdkhome.blzo.ex.basic.aop.api.ApiBeforeCustom;
import com.jdkhome.blzo.ex.risk.RiskChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by jdk on 2019/1/7.
 * 这里实现在请求LOG前面执行自定义代码
 */
@Slf4j
@Configuration
public class MyApiBeforeConfig {

    @Autowired
    RiskChecker riskChecker;

    @Primary
    @Bean
    ApiBeforeCustom myApiBeforeCustom() {
        return joinPoint -> riskChecker.check(joinPoint);
    }

}
