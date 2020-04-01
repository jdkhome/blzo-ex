package com.jdkhome.blzo.ex.basic.pojo;

import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.Data;

/**
 * author link.ji
 * createTime 上午11:18 2018/6/20
 * 基础返回
 */
@Data
public class ApiResponse<T> {

    private Integer code;
    private String msg;
    private Object debug;
    private T data;

    /**
     * 禁用直接创建
     */
    private ApiResponse() {
    }

    /**
     * 由BaseError 创建ApiResponse
     *
     * @param baseError
     */
    public ApiResponse(BaseError baseError) {
        this.code = baseError.getCode();
        this.msg = baseError.getMsg();
    }

    public ApiResponse(BaseError baseError, I18nEnums i18n) {
        this.code = baseError.getCode();
        this.msg = baseError.getMsg(i18n);
    }

    /**
     * 由BaseError 创建带有额外信息的ApiResponse
     *
     * @param baseError
     */
    public ApiResponse(BaseError baseError, T data) {
        this.code = baseError.getCode();
        this.msg = baseError.getMsg();
        this.data = data;
    }

    public ApiResponse(BaseError baseError, T data, I18nEnums i18n) {
        this.code = baseError.getCode();
        this.msg = baseError.getMsg(i18n);
        this.data = data;
    }


    /**
     * ServiceException 创建ApiResponse
     *
     * @param se
     */
    public ApiResponse(ServiceException se) {
        this.code = se.getErrorCode();
        this.msg = se.getErrorMsg();
        this.debug = se.getDebug();
    }

    public ApiResponse(ServiceException se, I18nEnums i18n) {
        this.code = se.getErrorCode();
        this.msg = se.getBaseError().getMsg(i18n);
        if (!se.getErrorCode().equals(se.getBaseError().getCode())) {
            this.msg = se.getErrorMsg();
        }
        this.debug = se.getDebug();
    }


    /**
     * 返回成功
     *
     * @param data
     * @return
     */
    static public ApiResponse<Object> success(Object data) {
        ApiResponse<Object> result = new ApiResponse<>(BasicResponseError.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功
     *
     * @return
     */
    static public ApiResponse success() {
        ApiResponse result = new ApiResponse(BasicResponseError.SUCCESS);
        result.setData(null);
        return result;
    }

    /**
     * 返回失败
     *
     * @param se
     * @return
     */
    static public ApiResponse error(ServiceException se) {
        return new ApiResponse(se);
    }

    static public ApiResponse error(ServiceException se, I18nEnums i18n) {
        return new ApiResponse(se, i18n);
    }

    /**
     * 返回失败
     *
     * @param baseError
     * @return
     */
    static public ApiResponse error(BaseError baseError) {
        return new ApiResponse(baseError);
    }

    static public ApiResponse error(BaseError baseError, I18nEnums i18n) {
        return new ApiResponse(baseError, i18n);
    }

    /**
     * 返回失败
     *
     * @param baseError
     * @return
     */
    static public ApiResponse error(BaseError baseError, Object debug) {
        return new ApiResponse(baseError, debug);
    }

    static public ApiResponse error(BaseError baseError, Object debug, I18nEnums i18n) {
        return new ApiResponse(baseError, debug, i18n);
    }

    /**
     * 返回未知失败
     *
     * @param e
     * @return
     */
    static public ApiResponse error(Exception e) {
        ApiResponse result = new ApiResponse(BasicResponseError.SERVER_ERROR);
        result.setDebug(e.getMessage());
        return result;
    }

    static public ApiResponse error(Exception e, I18nEnums i18n) {
        ApiResponse result = new ApiResponse(BasicResponseError.SERVER_ERROR, i18n);
        result.setDebug(e.getMessage());
        return result;
    }

    public void check() {
        if (!BasicResponseError.SUCCESS.getCode().equals(this.getCode())) {
            throw new ServiceException(this.getCode(), this.getMsg(), "[上游错误]" + this.getDebug(),
                    BasicResponseError.UPSTREAM_ERROR);
        }
    }

    public T get() {
        if (!BasicResponseError.SUCCESS.getCode().equals(this.getCode())) {
            throw new ServiceException(this.getCode(), this.getMsg(), "[上游错误]" + this.getDebug(),
                    BasicResponseError.UPSTREAM_ERROR);
        }
        return this.getData();
    }

}