package com.jdkhome.blzo.ex.version.annotation;


import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * author link.ji
 * createTime 下午5:09 2019/4/1
 * 新版版本号注解
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {

    /**
     * 版本号表达式
     *
     * @return
     */
    VersionEx[] value();

    /**
     * Declares the type filter to be used as an {@linkplain ComponentScan#includeFilters
     * include filter} or {@linkplain ComponentScan#excludeFilters exclude filter}.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface VersionEx {

        String value();

        Class<?> hander();

    }

}
