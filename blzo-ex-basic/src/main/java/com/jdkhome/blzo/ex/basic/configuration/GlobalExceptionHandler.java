package com.jdkhome.blzo.ex.basic.configuration;

import com.jdkhome.blzo.ex.basic.constants.BasicSystemConstants;
import com.jdkhome.blzo.ex.basic.enums.CommonResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.utils.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * see https://blog.csdn.net/kinginblue/article/details/70186586
     */


    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ApiResponse handleException(Exception e, HttpServletRequest request) {
        ApiResponse result = ApiResponse.error(e);
        log.error("Controller(Err) =>{} 未知异常 msg:{}", request.getAttribute(BasicSystemConstants.controllerName), e.getMessage(), e);
        log.info("Controller(Out) => {}", PerfectGson.getGson().toJson(result));
        return result;
    }

    /**
     * 处理参数错误 转换成自定义结构体
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    ApiResponse handleException(MissingServletRequestParameterException e, HttpServletRequest request) {
        ApiResponse result = ApiResponse.error(CommonResponseError.PARAMETER_ERROR, e.getMessage());
        return result;
    }

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    ApiResponse handleException(ServiceException e, HttpServletRequest request) {
        ApiResponse result = ApiResponse.error(e);
        log.error("Controller(Err) => {} 业务异常 msg:{}", request.getAttribute(BasicSystemConstants.controllerName), e.getErrorMsg());
        log.info("Controller(Out) => {}", PerfectGson.getGson().toJson(result));
        return result;
    }


}