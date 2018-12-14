package com.jdkhome.blzo.core.common.aop.version;

import com.jdkhome.blzo.ex.basic.pojo.BaseError;

/**
 * author link.ji
 * createTime 下午7:28 2018/6/21
 */
public enum VersionResponseError implements BaseError {

    //===========通用返回==============
    VERSION_UPGRADE(101, "需要升级版本");

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


    VersionResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
