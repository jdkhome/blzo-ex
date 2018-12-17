package com.jdkhome.blzo.ex.usignin.annotation;

import java.lang.annotation.*;

/**
 * Created by jdk on 17/9/6.
 * 对于一些也许需要用户登录的接口，采用这个注解，如果header中有有效的token则会解析出userId
 * 否则userId=null 但是不会拦截请求
 */
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserMaybeSignin {
}
