package com.jdkhome.blzo.ex.basic.exception;


import com.jdkhome.blzo.ex.basic.pojo.BaseError;
import com.jdkhome.blzo.ex.basic.risk.RiskTigger;
import lombok.Getter;

/**
 * author link.ji
 * createTime 下午5:38 2018/6/21
 */
@Getter
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    //异常码
    private Integer errorCode;

    //异常信息
    private String errorMsg;

    //更多信息
    private Object debug;

    //是否触发风控
    private RiskTigger riskTigger;

    //返回给前端的错误信息
    private BaseError baseError;

    public ServiceException() {
        this.errorCode = null;
        this.errorMsg = null;
        this.debug = null;
        this.baseError = null;
        this.riskTigger = null;
    }

    public ServiceException(BaseError baseError) {
        this.errorCode = baseError.getCode();
        this.errorMsg = baseError.getMsg();
        this.debug = null;
        this.baseError = baseError;
        this.riskTigger = null;
    }

    public ServiceException(BaseError baseError, Object debug) {
        this.errorCode = baseError.getCode();
        this.errorMsg = baseError.getMsg();
        this.debug = debug;
        this.baseError = baseError;
        this.riskTigger = null;
    }

    public ServiceException(BaseError baseError, Object debug, RiskTigger riskTigger) {
        this.errorCode = baseError.getCode();
        this.errorMsg = baseError.getMsg();
        this.debug = debug;
        this.baseError = baseError;
        this.riskTigger = riskTigger;
    }

    public ServiceException(Integer errorCode, String errorMsg, Object debug, BaseError baseError) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.debug = debug;
        this.baseError = baseError;
    }


}
