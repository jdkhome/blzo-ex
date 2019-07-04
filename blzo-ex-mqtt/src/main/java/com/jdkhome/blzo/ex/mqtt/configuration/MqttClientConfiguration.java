package com.jdkhome.blzo.ex.mqtt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * author linkji.
 * create at 2019-07-04 10:08
 */
@Configuration
public class MqttClientConfiguration {


    @Value("${mqtt.host}")
    String host;

    @Value("${mqtt.username}")
    String userName;

    @Value("${mqtt.password}")
    String password;


    /**
     * 如果是基于spring-cloud consul
     * 十分建议使用 spring.cloud.consul.discovery.instanceId
     * mqtt:
     * clientId: ${spring.cloud.consul.discovery.instanceId}
     */
    @Value("${mqtt.clientId}")
    String clientId;

    @Bean
    MqttClient mqttClient() throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        MqttClient client = new MqttClient(host, clientId, persistence);
        //client.setCallback(new PushCallback());
        client.connect(options);
        return client;

    }
}
