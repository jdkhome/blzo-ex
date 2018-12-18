package com.jdkhome.blzo.ex.authj.enums;


import com.jdkhome.blzo.ex.basic.pojo.BaseError;

/**
 * author link.ji
 * createTime 上午11:13 2018/6/20
 */
public enum AuthjResponseError implements BaseError {

    RESP_ERROR_ADMIN_NOT_EXIST(10000, "管理员不存在"),
    RESP_ERROR_PASSWORD_ERROR(10001, "管理员密码错误");

    private Integer code;
    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }


    AuthjResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
