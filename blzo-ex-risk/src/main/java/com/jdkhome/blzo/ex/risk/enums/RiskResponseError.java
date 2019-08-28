package com.jdkhome.blzo.ex.risk.enums;

import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import com.jdkhome.blzo.ex.basic.pojo.BaseError;
import lombok.Getter;

/**
 * author link.ji
 * createTime 下午7:28 2018/6/21
 */
@Getter
public enum RiskResponseError implements BaseError {

    RISK(-1, "请不要搞事");

    private Integer code;
    private String msg;

    public String getMsg(I18nEnums i18n) {
        return msg;
    }

    RiskResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
