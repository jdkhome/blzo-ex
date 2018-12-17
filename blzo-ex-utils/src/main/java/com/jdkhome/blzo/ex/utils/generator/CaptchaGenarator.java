package com.jdkhome.blzo.ex.utils.generator;

import java.util.Random;

/**
 * Created by jdk on 17/5/5.
 * 验证码生成器
 */
public class CaptchaGenarator {

    public static String generateNum(int length) {
        String chars = "0123456789";
        char[] rands = new char[length];
        for (int i = 0; i < length; i++) {
            rands[i] = chars.charAt(new Random().nextInt(10));
        }
        return String.valueOf(rands);
    }

    public static String generateCode(int length) {
        String chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
        char[] rands = new char[length];
        for (int i = 0; i < length; i++) {
            rands[i] = chars.charAt(new Random().nextInt(32));
        }
        return String.valueOf(rands);
    }

    public static String generateFourNumCaptcha() {
        return CaptchaGenarator.generateNum(4);
    }

    public static String generateSixNumCaptcha() {
        return CaptchaGenarator.generateNum(6);
    }

    public static String generateFourCodeCaptcha() {
        return CaptchaGenarator.generateCode(4);
    }

    public static String generateSixCodeCaptcha() {
        return CaptchaGenarator.generateCode(6);
    }

}
