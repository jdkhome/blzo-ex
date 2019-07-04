package com.jdkhome.blzo.ex.version.annotation;

import com.jdkhome.blzo.ex.version.constants.VersionConstants;

import java.lang.annotation.*;

/**
 * author link.ji
 * createTime 下午5:09 2018/6/19
 * 加了version注解的接口，必须在header中传入version="x.x.x"
 * 没有传入version 或者version版本过老的接口，将会直接返回"需要升级版本"
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinVersion {

    /**
     * 最小支持版本
     *
     * @return
     */
    String value();

    /**
     * 最大支持版本
     * 当新版产品,移除了某些功能,导致一些接口准备删除时,
     * 可以指定该接口的最大支持版本,高版本的客户端不能调用超过最大支持版本的接口,
     * 便于未来删除
     *
     * @return
     */
    String max() default VersionConstants.ANY_VERSION_CODE;
}
