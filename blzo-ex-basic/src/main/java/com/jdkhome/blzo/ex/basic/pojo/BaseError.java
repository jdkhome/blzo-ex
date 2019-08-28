package com.jdkhome.blzo.ex.basic.pojo;

import com.jdkhome.blzo.ex.basic.enums.I18nEnums;

/**
 * author link.ji
 * createTime 上午11:19 2018/6/20
 */
public interface BaseError {

    Integer getCode();

    String getMsg();

    // 多语言
    String getMsg(I18nEnums i18n);
}
