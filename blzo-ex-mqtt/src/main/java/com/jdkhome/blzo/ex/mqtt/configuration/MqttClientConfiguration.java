package com.jdkhome.blzo.ex.mqtt.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

/**
 * author linkji.
 * create at 2019-07-04 10:08
 */
@Slf4j
@Data
@Component
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

    @Value("${mqtt.publish_url}")
    String publishUrl;

    @Value("${mqtt.app_id}")
    String appId;

    @Value("${mqtt.app_key}")
    String appKey;

    String authorization;

    @Autowired
    PushCallback pushCallback;

    // 保存连接
    MqttClient client;

    private MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        return options;
    }

    // bean初始化 (对象创建之后) 自动执行
    @PostConstruct
    public void reConnect() throws MqttException {

        authorization = "Basic " + Base64.getEncoder().encodeToString((appId + ":" + appKey).getBytes());

        //防止重复创建MQTTClient实例
        if (client == null) {
            client = new MqttClient(host, clientId, new MemoryPersistence());
            client.setCallback(pushCallback);
        }

        //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
        if (!client.isConnected()) {
            client.connect(this.getOptions());
            log.info("[MQTT] -> 连接成功");
        } else {//这里的逻辑是如果连接成功就重新连接
            client.disconnect();
            // 手动释放
            client = null;
            client = new MqttClient(host, clientId, new MemoryPersistence());
            client.setCallback(pushCallback);
            client.connect(this.getOptions());
            log.info("[MQTT] -> 重连成功");
        }
    }

    /**
     * 获取连接
     *
     * @return
     */
    public MqttClient get() {

        if (client == null) {
            log.warn("[MQTT] -> 还未完成加载");
        }

        return client;
    }

}
