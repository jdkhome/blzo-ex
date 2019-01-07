package com.jdkhome.blzo.ex.basic.aop.api;

import org.aspectj.lang.JoinPoint;

/**
 * Created by jdk on 2019/1/7.
 */
public interface ApiBeforeCustom {

    void action(JoinPoint joinPoint);
}
