package com.jdkhome.blzo.ex.mqtt;

import lombok.Data;

/**
 * author linkji.
 * create at 2019-07-10 14:02
 */
@Data
public class HttpSendRequest {

    Integer qos;

    // 是否保持会话
    Boolean retain;


    String client_id;

    // 主题
    String topic;

    // 内容
    String payload;


}
