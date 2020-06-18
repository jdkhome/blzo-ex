package com.jdkhome.demo.controller;

import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * create by linkji on 11:09 上午 2020/3/27
 * 基础请求演示
 */
@Slf4j
@RestController
@RequestMapping("/api/basic/demo")
public class BasicDemoController {

    /**
     * /api/basic/demo/api1
     * 此接口演示自动日志以及参数校验
     */
    @Data
    static class BasicDemoApi1Params {
        @NotBlank(message = "用户名不能为空")
        String name;

        @NotBlank(message = "手机号不能为空")
        String phone;
    }

    @Api("基础测试接口")
    @RequestMapping("/api1")
    public ApiResponse apiBasicDemoApi1(@Valid @RequestBody BasicDemoApi1Params params) {
        return ApiResponse.success();
    }
}
