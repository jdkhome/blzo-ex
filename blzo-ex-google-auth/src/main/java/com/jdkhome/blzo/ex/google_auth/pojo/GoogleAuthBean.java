package com.jdkhome.blzo.ex.google_auth.pojo;

import lombok.Data;

/**
 * author linkji.
 * create at 2019-07-17 10:51
 */
@Data
public class GoogleAuthBean {

    /**
     * 服务端保存秘钥
     */
    String secret;

    /**
     * 客户端扫描二维码
     */
    String qrCode;
}
