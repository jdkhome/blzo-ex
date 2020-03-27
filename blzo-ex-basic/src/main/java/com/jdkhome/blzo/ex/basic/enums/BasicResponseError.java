package com.jdkhome.blzo.ex.basic.enums;

import com.jdkhome.blzo.ex.basic.pojo.BaseError;
import lombok.Getter;

/**
 * author link.ji
 * createTime 下午7:28 2018/6/21
 */
@Getter
public enum BasicResponseError implements BaseError {

    //===========通用返回==============
    SUCCESS(200, "success", "success"),
    PARAMETER_ERROR(400, "参数错误", "params error"),
    NO_LOGIN(401, "请先登录", "no login"),
    NO_PERMISSION(403, "权限不足", "no rermission"),
    NOT_FOUND(404, "资源不存在", "not found"),
    SERVER_ERROR(500, "服务器未知错误", "server error"),
    SERVER_BUSY(503, "服务器忙", "server busy"),
    TIME_OUT(504, "执行超时", "time out"),
    UPSTREAM_ERROR(600, "上游错误", "upstream error"),
    TODO(666, "未实现", "todo"),
    ;

    private Integer code;
    private String msg;

    private String enMsg;

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

    BasicResponseError(int code, String msg, String enMsg) {
        this.code = code;
        this.msg = msg;
        this.enMsg = enMsg;
    }
}
