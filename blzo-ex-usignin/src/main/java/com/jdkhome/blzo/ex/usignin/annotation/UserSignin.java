package com.jdkhome.blzo.ex.usignin.annotation;

import java.lang.annotation.*;

/**
 * Created by jdk on 17/9/6.
 * 需要登录权限的请求需要添加该注解，拦截器会拦截该注解指定的方法进行token校验等
 *
 */
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserSignin {
}
