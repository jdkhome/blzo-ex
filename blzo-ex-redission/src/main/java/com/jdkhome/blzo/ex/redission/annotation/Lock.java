package com.jdkhome.blzo.ex.redission.annotation;

import java.lang.annotation.*;

/**
 * 该注解加到方法上xxxx
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

}
