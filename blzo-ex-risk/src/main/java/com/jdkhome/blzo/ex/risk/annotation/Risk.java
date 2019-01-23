package com.jdkhome.blzo.ex.risk.annotation;

import java.lang.annotation.*;

/**
 * Created by jdk on 17/9/6.
 * 需要登录权限的请求需要添加该注解，拦截器会拦截该注解指定的方法进行token校验等
 */
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Risk {

    boolean risk() default true;

    /**
     * 多长时间内
     *
     * @return
     */
    long period();

    /**
     * 请求多少次
     *
     * @return
     */
    int count();

    /**
     * 风控多长时间
     *
     * @return
     */
    long time();

}
