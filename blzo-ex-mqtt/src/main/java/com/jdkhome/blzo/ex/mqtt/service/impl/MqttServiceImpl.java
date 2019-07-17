package com.jdkhome.blzo.ex.mqtt.service.impl;

import com.github.kevinsawicki.http.HttpRequest;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.mqtt.HttpSendRequest;
import com.jdkhome.blzo.ex.mqtt.configuration.MqttClientConfiguration;
import com.jdkhome.blzo.ex.mqtt.enums.MqttQosEnum;
import com.jdkhome.blzo.ex.mqtt.service.MqttService;
import com.jdkhome.blzo.ex.utils.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author linkji.
 * create at 2019-07-04 10:21
 */
@Slf4j
@Service
public class MqttServiceImpl implements MqttService {

    @Autowired
    MqttClientConfiguration mqttClientConfiguration;

    /**
     * 默认发送 default qos = 0
     * 相当于面向客户端的redis发布订阅，不保证送达，适用于行情等推送
     *
     * @param topic
     * @param msg
     */
    @Override
    public void send(String topic, String msg) {
        this.send(topic, msg, MqttQosEnum.QoS0);
    }

    @Override
    public void httpSent(String topic, String msg) {
        log.info("[MQTT] 发送(http)-> topic:{} message:{}", topic, msg);
        HttpSendRequest httpSendRequest = new HttpSendRequest();
        httpSendRequest.setClient_id(mqttClientConfiguration.getClientId());
        httpSendRequest.setQos(MqttQosEnum.QoS0.getCode());
        httpSendRequest.setRetain(false);
        httpSendRequest.setTopic(topic);
        httpSendRequest.setPayload(msg);

        String result = HttpRequest.post(mqttClientConfiguration.getPublishUrl())
                .authorization(mqttClientConfiguration.getAuthorization())
                .send(PerfectGson.getGson().toJson(httpSendRequest)).body();

        log.debug("[MQTT] result={}", result);

    }

    /**
     * QoS 0: 最多分发一次 默认
     * QoS 1: 至少分发一次
     * QoS 2: 仅分发一次
     *
     * @param topic
     * @param msg
     * @param qos
     */
    @Override
    public void send(String topic, String msg, MqttQosEnum qos) {
        if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(msg)) {
            log.error("[MQTT] 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        if (qos == null) {
            qos = MqttQosEnum.QoS0;
        }

        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(qos.getCode());
        message.setRetained(false);
        try {
            log.info("[MQTT] 发送-> topic:{} message:{}", topic, msg);
            mqttClientConfiguration.get().publish(topic, message);
        } catch (MqttException e) {
            log.error("[MQTT] 发送失败-> e:{}", e.getMessage());
            throw new ServiceException(BasicResponseError.UPSTREAM_ERROR, e.getMessage());
        }
    }
}
