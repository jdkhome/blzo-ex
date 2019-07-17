package com.jdkhome.blzo.ex.google_auth;

import com.jdkhome.blzo.ex.google_auth.pojo.GoogleAuthBean;

/**
 * author linkji.
 * create at 2019-07-17 10:50
 */
public class GoogleAuth {

    /**
     * 生成验证秘钥和二维码
     * @param user 用户标识
     * @return
     */
    public static GoogleAuthBean generator(String user) {
        GoogleAuthBean bean = new GoogleAuthBean();
        bean.setSecret(GoogleGenerator.generateSecretKey());
        bean.setQrCode(GoogleGenerator.getQRBarcode(user, bean.getSecret()));
        return bean;
    }

    /**
     * 验证
     * @param secret
     * @param code
     * @return
     */
    public static Boolean validCode(String secret, String code) {
        long time = System.currentTimeMillis();
        GoogleGenerator g = new GoogleGenerator();
        return g.check_code(secret, code, time);
    }
}
