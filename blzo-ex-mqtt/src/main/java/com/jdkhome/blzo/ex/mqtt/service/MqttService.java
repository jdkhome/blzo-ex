package com.jdkhome.blzo.ex.mqtt.service;

import com.jdkhome.blzo.ex.mqtt.enums.MqttQosEnum;

/**
 * author linkji.
 * create at 2019-07-04 10:07
 * thanks https://blog.csdn.net/zs40122/article/details/80003898
 */
public interface MqttService {


    /**
     * 默认发送 default qos = 0
     * 相当于面向客户端的redis发布订阅，不保证送达，适用于行情等推送
     *
     * @param topic
     * @param msg
     */
    void send(String topic, String msg);

    /**
     * QoS 0: 最多分发一次 默认
     * QoS 1: 至少分发一次
     * QoS 2: 仅分发一次
     *
     * @param topic
     * @param msg
     * @param qos
     */
    void send(String topic, String msg, MqttQosEnum qos);
}
