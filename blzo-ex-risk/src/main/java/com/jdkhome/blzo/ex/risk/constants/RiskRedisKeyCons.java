package com.jdkhome.blzo.ex.risk.constants;

/**
 * Created by jdk on 2019/1/23.
 */
public class RiskRedisKeyCons {

    /**
     * 该记录表示，某个uri  ip 被风控
     */
    public static final String RISK_VALID_IP_URI = "RISK:VALID:%s:%s";

    /**
     * 该记录用于统计风控记录
     */
    public static final String RISK_RECORD_IP_URI_TIME = "RISK:RECORD:%s:%s:%s";

    public static final String RISK_RECORD = "RISK:RECORD:*";

}