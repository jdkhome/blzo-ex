package com.jdkhome.demo.controller;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.version.annotation.MinVersion;
import com.jdkhome.blzo.ex.version.annotation.Version;
import com.jdkhome.blzo.ex.version.tools.VersionTools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * create by linkji on 11:14 上午 2020/3/27
 * 版本控制
 * 客户端需要写死版本号，然后在请求时的header中带上version=xxx 以此向服务端提供当前的版本号
 */
@Slf4j
@RestController
@RequestMapping("/api/version/demo")
public class VersionDemoController {

    /**
     * /api/version/demo/api1
     * 此接口演示版本控制
     * <p>
     * 在我们提前开发未来版本的接口时，就应该定义最小版本限制
     * 在我们计划某个版本弃用接口时，就应该定义最大版本限制
     * <p>
     * 不在版本设计范围内的请求均会被拒绝，这样我们就可以在测试期间就检查出
     * 新版客户端仍然调用老的废弃接口的情况
     * <p>
     * 另外一个场景是，某个功能我们希望改版，但是又不希望做向下兼容，此时就希望进行强制升级，此时只需将改功能的最小版本限制提高到最新版本
     * 此时旧版客户端的请求就都会失败，并获得需要升级版本的错误码
     */
    @MinVersion(value = "1.1.1", max = "1.5.6") // 由于历史原因。。这个注解名字是"MinVersion" 实际可同时限制最大最小版本
    @Api("版本控制测试")
    @RequestMapping("/api1")
    public ApiResponse apiVersionDemoApi1(@Version String version) {

        /**
         * 方法中可通过 @Version 参数拿到客户端的具体版本，这样我们就可以根据不同版本进行不同的处理
         */

        if (VersionTools.checkVersion(version, "1.3.0")) {
            // 当请求版本号 大于等于 1.3.0
            return ApiResponse.success("高版本响应");
        } else {
            // 当请求版本号 小于 1.3.0
            return ApiResponse.success("低版本响应");
        }
    }
}
