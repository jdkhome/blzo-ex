package com.jdkhome.blzo.ex.usignin.token;

/**
 * Created by jdk on 17/9/6.
 * token管理器
 */
public interface TokenManager {


    /**
     * 创建一个token关联上指定用户
     *
     * @param userId 指定用户的id
     * @return 生成的token
     */
    String createToken(Integer userId);


    /**
     * 检查token是否有效
     *
     * @param token
     * @return 是否有效
     */
    boolean checkToken(String token);


    /**
     * 根据token获取用户ID
     *
     * @param token
     * @return 用户ID
     */
    Integer getUserId(String token);

    /**
     * 清除用户的Token
     *
     * @param userId
     */
    void deleteTokenByUserId(Integer userId);

    /**
     * 清除token
     *
     * @param token 登录秘钥
     */
    void deleteToken(String token);

    /**
     * 刷新token 有效期
     * @param token
     */
    void refreshTokenExpire(String token);


}
