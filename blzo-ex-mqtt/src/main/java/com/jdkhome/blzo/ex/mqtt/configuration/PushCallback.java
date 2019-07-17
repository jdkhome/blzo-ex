package com.jdkhome.blzo.ex.mqtt.configuration;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MqttPush回调
 */
@Slf4j
@Component
public class PushCallback implements MqttCallback {

    @Autowired
    MqttClientConfiguration mqttClientConfiguration;

    @Override
    public void connectionLost(Throwable throwable) {


        while (true) {
            log.error("[MQTT] -> 连接丢失 尝试重连");
            try {
                mqttClientConfiguration.reConnect();
                break;
            } catch (Exception e) {
                log.error("[MQTT] -> 重连失败");
            }

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
//        System.out.println("Client 接收消息主题 : " + topic);
//        System.out.println("Client 接收消息Qos : " + message.getQos());
//        System.out.println("Client 接收消息内容 : " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}