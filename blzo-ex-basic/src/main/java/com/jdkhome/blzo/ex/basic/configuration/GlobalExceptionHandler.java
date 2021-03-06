package com.jdkhome.blzo.ex.basic.configuration;

import com.jdkhome.blzo.ex.basic.constants.BasicSystemConstants;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.basic.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jdk on 2019/1/7.
 * 异常处理器
 * 处理从Controller抛出的异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * see https://blog.csdn.net/kinginblue/article/details/70186586
     */
    private void errorPage(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        String uri = request.getServletPath();
        if (!uri.startsWith("/api")) {
            // page 跳转到错误页面
            throw e;
        }

    }

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ApiResponse handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {

        I18nEnums i18n = I18nEnums.getByCode(request.getHeader(BasicSystemConstants.i18n));
        ApiResponse result = ApiResponse.error(e, i18n);

        log.error("Controller(Err) =>{} 未知异常 msg:{}", request.getAttribute(BasicSystemConstants.controllerName), e.getMessage(), e);
        log.info("Controller(Out) => {}", PerfectGson.getGson().toJson(result));

        this.errorPage(request, response, e);
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
    ApiResponse handleException(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) throws Exception {

        I18nEnums i18n = I18nEnums.getByCode(request.getHeader(BasicSystemConstants.i18n));
        ApiResponse result = ApiResponse.error(BasicResponseError.PARAMETER_ERROR, e.getMessage(), i18n);

        this.errorPage(request, response, e);
        return result;
    }

    /**
     * 处理参数校验 转换成自定义结构体
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ApiResponse handleException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        I18nEnums i18n = I18nEnums.getByCode(request.getHeader(BasicSystemConstants.i18n));
        ApiResponse result = ApiResponse.error(BasicResponseError.PARAMETER_ERROR, e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), i18n);
        this.errorPage(request, response, e);
        return result;
    }

    /**
     * 处理参数错误 转换成自定义结构体
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    ApiResponse handleException(BindException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        I18nEnums i18n = I18nEnums.getByCode(request.getHeader(BasicSystemConstants.i18n));
        String msg = (e.getBindingResult()).getAllErrors().get(0).getDefaultMessage();
        ApiResponse result = ApiResponse.error(BasicResponseError.PARAMETER_ERROR,msg , i18n);
        log.error("Controller(Err) => {} 参数校验不通过 msg:{}", request.getAttribute(BasicSystemConstants.controllerName),msg);
        log.info("Controller(Out) => {}", PerfectGson.getGson().toJson(result));

        this.errorPage(request, response, e);
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
    ApiResponse handleException(ServiceException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        I18nEnums i18n = I18nEnums.getByCode(request.getHeader(BasicSystemConstants.i18n));
        ApiResponse result = ApiResponse.error(e, i18n);
        log.error("Controller(Err) => {} 业务异常 msg:{}", request.getAttribute(BasicSystemConstants.controllerName), e.getErrorMsg());
        log.info("Controller(Out) => {}", PerfectGson.getGson().toJson(result));

        this.errorPage(request, response, e);
        return result;
    }


}