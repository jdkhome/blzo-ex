package com.jdkhome.blzo.ex.usignin.token.impl;

import com.jdkhome.blzo.ex.usignin.constants.UsigninConstants;
import com.jdkhome.blzo.ex.usignin.token.TokenManager;
import com.jdkhome.blzo.ex.utils.generator.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdk on 17/9/6.
 * 基于redis的token管理器
 */
@Component
public class RedisTokenManager implements TokenManager {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 缓存token，有效期7天
     * {key=token, value=timeStamp}
     *
     * @param token 验证码
     */
    private void setToken(String token, Integer userId) {
        String cachedKey = String.format(UsigninConstants.CACHE_KEY, token);
        stringRedisTemplate.opsForValue().set(cachedKey, String.valueOf(userId), 3600L * 24 * 7, TimeUnit.SECONDS);
        stringRedisTemplate.opsForList().leftPush(String.format(UsigninConstants.TOKEN_MAPPER, userId.toString()), token);
    }

    /**
     * 获取指定用户的token
     *
     * @param userId
     */
    private String getToken(Integer userId) {

        Object obj = stringRedisTemplate.opsForList().range(String.format(UsigninConstants.TOKEN_MAPPER, userId.toString()), 0, 0);
        return obj == null ? null : String.valueOf(obj);

    }

    /**
     * 判断token是否存在
     *
     * @param
     */
    private Boolean hasToken(String token) {
        String cachedKey = String.format(UsigninConstants.CACHE_KEY, token);
        return stringRedisTemplate.opsForValue().get(cachedKey) != null;
    }

    /**
     * 删除token
     * {key=token, value=timeStamp}
     *
     * @param token 验证码
     */
    private void delToken(String token) {
        String cachedKey = String.format(UsigninConstants.CACHE_KEY, token);
        stringRedisTemplate.delete(cachedKey);
    }




    /**
     * 删除该用户的所有Token
     *
     * @param userId
     */
    private void delAllTokenByUserId(Integer userId) {
        List<String> tokenList = stringRedisTemplate.opsForList().range(String.format(UsigninConstants.TOKEN_MAPPER, userId.toString()), 0, -1);
        tokenList.stream().forEach(token -> delToken(token));
        stringRedisTemplate.delete(String.format(UsigninConstants.TOKEN_MAPPER, userId.toString()));
    }

    @Override
    public String createToken(Integer userId) {

        //使用uuid作为源token
        String token = UUIDGenerator.get();


        //存储到redis并设置过期时间
        this.setToken(token, userId);

        return token;
    }

    @Override
    public boolean checkToken(String token) {

        if (token == null) {
            return false;
        }

        Boolean hasToken = this.hasToken(token);

        if (hasToken) {
            //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
            this.refreshTokenExpire(token);
        }

        return hasToken;
    }

    /**
     * 清除用户的Token
     *
     * @param userId
     */
    @Override
    public void deleteTokenByUserId(Integer userId) {
        this.delAllTokenByUserId(userId);
    }

    @Override
    public void deleteToken(String token) {
        this.delToken(token);
    }

    @Override
    public Integer getUserId(String token) {

        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String cachedKey = String.format(UsigninConstants.CACHE_KEY, token);
        String userId = stringRedisTemplate.opsForValue().get(cachedKey);
        if (userId != null) {
            return Integer.valueOf(userId);
        } else {
            return null;
        }
    }

    /**
     * 延长token过期时间
     */
    @Override
    public void refreshTokenExpire(String token) {
        Integer userId = this.getUserId(token);
        this.setToken(token, userId);
    }
}
