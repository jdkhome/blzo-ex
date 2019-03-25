package com.jdkhome.blzo.ex.basic.configuration;

import com.jdkhome.blzo.ex.basic.constants.BasicSystemConstants;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.utils.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        ApiResponse result = ApiResponse.error(e);
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
        ApiResponse result = ApiResponse.error(BasicResponseError.PARAMETER_ERROR, e.getMessage());

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
        ApiResponse result = ApiResponse.error(e);
        log.error("Controller(Err) => {} 业务异常 msg:{}", request.getAttribute(BasicSystemConstants.controllerName), e.getErrorMsg());
        log.info("Controller(Out) => {}", PerfectGson.getGson().toJson(result));

        this.errorPage(request, response, e);
        return result;
    }


}