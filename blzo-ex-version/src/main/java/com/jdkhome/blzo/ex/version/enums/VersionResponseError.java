package com.jdkhome.blzo.ex.version.enums;

import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import com.jdkhome.blzo.ex.basic.pojo.BaseError;
import lombok.Getter;

/**
 * author link.ji
 * createTime 下午7:28 2018/6/21
 */
@Getter
public enum VersionResponseError implements BaseError {

    //===========通用返回==============
    VERSION_UPGRADE(101, "版本过低","version is too low"),
    API_DEPRECATED(102, "接口已过期","interface expired");

    private Integer code;
    private String msg;
    private String enMsg;


    // 多语言适配
    @Override
    public String getMsg(I18nEnums i18n) {
        if (i18n == null) {
            return msg;
        }
        switch (i18n) {
            case EN_US:
                return enMsg;
            default:
                return msg;
        }
    }

    VersionResponseError(int code, String msg, String enMsg) {
        this.code = code;
        this.msg = msg;
        this.enMsg = enMsg;
    }
}
