package com.jdkhome.blzo.ex.authj.enums;


import com.jdkhome.blzo.ex.basic.pojo.BaseError;

/**
 * author link.ji
 * createTime 上午11:13 2018/6/20
 */
public enum AuthjResponseError implements BaseError {

    ADMIN_NOT_EXIST(10000, "管理员不存在"),
    PASSWORD_ERROR(10001, "管理员密码错误"),
    ADMIN_STATUS_ERROR(10002, "管理员状态错误"),
    ACTIVE_LIKE_ORIGIN(10003, "新密码不能与原密码相同"),
    ADMIN_HAVENOT_ORG(10004, "管理员无组织"),
    ;

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
