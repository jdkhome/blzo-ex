package com.jdkhome.blzo.ex.usignin.token.impl;

import com.jdkhome.blzo.ex.basic.exception.ServiceException;
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
 * Created by tenson on 2020/03/27.
 * 基于jwt的token管理器
 */
@Component
public class JwtTokenManager implements TokenManager {


    @Override
    public String createToken(Object userId) {
        // todo:
        //  1。根据用户ID生成jwt token
        //  2。有效期从配置文件中获取
        //  3。默认有效期为1周
        return null;
    }

    @Override
    public boolean checkToken(String token) {
        // todo: 检查jwt token 是否有效
        return false;
    }

    @Override
    public Object getUserId(String token) {
        // todo: 从jwt token中获取用户ID
        return null;
    }

    @Override
    public void deleteTokenByUserId(Object userId) {
        // not support for jwt token manager
       throw new UnsupportedOperationException();
    }

    @Override
    public void deleteToken(String token) {
        // not support for jwt token manager
        throw new UnsupportedOperationException();
    }

    @Override
    public void refreshTokenExpire(String token) {
        // not support for jwt token manager
        throw new UnsupportedOperationException();
    }
}
