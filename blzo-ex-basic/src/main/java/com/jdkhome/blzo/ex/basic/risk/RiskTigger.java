package com.jdkhome.blzo.ex.basic.risk;

import lombok.Data;

@Data
public class RiskTigger {

    /**
     * 风控问题描述
     */
    String msg;

    /**
     * 禁止操作时间 单位秒
     */
    Long time;

    public RiskTigger() {
    }


    // TODO 这个地方 考虑用枚举
    public RiskTigger(String msg, Long time) {
        this.msg = msg;
        this.time = time;
    }

}