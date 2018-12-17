package com.jdkhome.blzo.ex.usignin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jdk on 17/9/6.
 * 需要获取当前用户时添加该注解，自定义参数解析器会解析token比获取该参数
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
