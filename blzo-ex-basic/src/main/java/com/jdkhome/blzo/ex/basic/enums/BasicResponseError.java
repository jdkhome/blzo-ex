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
    NO_LOGIN(100, "请先登录"),
    SUCCESS(200, "success"),
    PARAMETER_ERROR(400, "参数错误"),
    NO_PERMISSION(403, "权限不足"),
    SERVER_ERROR(500, "服务器未知错误"),
    SERVER_BUSY(503, "服务器忙"),
    TIME_OUT(504, "执行超时"),
    UPSTREAM_ERROR(600, "上游错误"),
    TODO(666, "未实现"),

    ;

    private Integer code;
    private String msg;

    BasicResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
