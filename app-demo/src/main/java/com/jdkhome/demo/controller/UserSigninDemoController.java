package com.jdkhome.demo.controller;

import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.usignin.annotation.CurrentUser;
import com.jdkhome.blzo.ex.usignin.annotation.UserMaybeSignin;
import com.jdkhome.blzo.ex.usignin.annotation.UserSignin;
import com.jdkhome.blzo.ex.usignin.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by linkji on 11:40 上午 2020/3/27
 * 用户登陆业务演示
 */
@Slf4j
@RestController
@RequestMapping("/api/user_signin/demo")
public class UserSigninDemoController {

    @Autowired
    @Qualifier("redisTokenManager")
    TokenManager redisTokenManager;

//    @Autowired
//    @Qualifier("jwtTokenManager")
//    TokenManager jwtTokenManager;

    /**
     * /api/user_signin/demo/signin
     * 登陆接口，省略了 用户名密码等参数，这里不限制登陆方式，短信登陆、第三方授权登陆等等都可以
     * 你要做的就是 为登陆的这个用户 申请一个token
     *
     * 之后客户端请求时 需要在header中带上 token=xxxx
     */

    @Api("登陆接口")
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ApiResponse apSignin() {

        // ...
        Integer userId = 123; // 通过一系列你的业务，拿到了用户id
        // ...

        // 使用用户id申请token
        String token = redisTokenManager.createToken(userId);

        return ApiResponse.success(token);
    }

    /**
     * /api/user_signin/demo/must_be_signin
     * 在controller方法上加上@UserSignin注解 表示使用该接口需要用户登录
     * 在方法参数中加入 @CurrentUser Integer userId ，以获取调用该接口的用户Id userId一定不会为空
     */

    @UserSignin
    @Api("必须登陆才可使用的接口")
    @RequestMapping(value = "/must_be_signin", method = RequestMethod.POST)
    public ApiResponse apiMustBeSignin(@CurrentUser Integer userId) {

        return ApiResponse.success(userId);
    }

    /**
     * /api/user_signin/demo/maybe_signin
     * 在controller方法上加上@UserMaybeSignin注解 表示使用该接口需要用户登录，也可以不登录
     * 在方法参数中加入 @CurrentUser Integer userId ，以获取调用该接口的用户Id userId可能为空
     */

    @UserMaybeSignin
    @Api("登不登陆都可以使用的接口")
    @RequestMapping(value = "/maybe_signin", method = RequestMethod.POST)
    public ApiResponse apiMaybeSignin(@CurrentUser Integer userId) {

        return ApiResponse.success(userId);
    }
}
