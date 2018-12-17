package com.jdkhome.blzo.ex.utils.generator;

import java.util.UUID;

/**
 * Created by jdk on 17/11/20.
 * uuid 生成器 剔除掉uuid中的"-"
 */
public class UUIDGenerator {

    public static String get() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
