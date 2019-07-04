package com.jdkhome.blzo.ex.mqtt.enums;

import lombok.Getter;

/**
 * author link.ji
 * createTime 下午4:05 2018/5/10
 * QoS
 */
@Getter
public enum MqttQosEnum {

    QoS0(0, "最多分发一次,类似Redis发布订阅"),
    QoS1(1, "至少分发一次,需要必达操作的幂等操作"),
    QoS2(2, "仅分发一次,需要必达的不可重复消费操作"),
    ;


    Integer code;
    String name;

    MqttQosEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
