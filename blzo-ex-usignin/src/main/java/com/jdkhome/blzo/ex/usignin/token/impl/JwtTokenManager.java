package com.jdkhome.blzo.ex.usignin.token.impl;

import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.usignin.constants.UsigninConstants;
import com.jdkhome.blzo.ex.usignin.token.TokenManager;
import com.jdkhome.blzo.ex.utils.generator.UUIDGenerator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tenson on 2020/03/27.
 * 基于jwt的token管理器
 */

@ConfigurationProperties("jwt")
public class JwtTokenManager implements TokenManager {

    /**
     * 发布者
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * 密钥
     *
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * 生存期
     */
    @Value("${jwt.ttlMills}")
    private Long ttlMills;
    /**
     * 默认生存期30天
     */
    private static final long DEFAULT_TTL_MILLIS = 30L * 24L * 60L * 60L * 1000L;

    private static final String USER_ID_KEY = "userId";



    @Override
    public String createToken(Object userId) {

        //  1。根据用户ID生成jwt token
        //  2。有效期从配置文件中获取
        //  3。默认有效期为30天

        DefaultClaims claims = new DefaultClaims();
        long loginTime = System.currentTimeMillis();
        if(null == ttlMills) ttlMills = DEFAULT_TTL_MILLIS;
        long expire = loginTime + ttlMills;
        Date expiration = new Date(expire); // 过期日期
        Date nowDate = new Date();
        claims.setIssuer(issuer)
                .setExpiration(expiration)
                .setIssuedAt(nowDate)
                .setNotBefore(nowDate)
                .put(USER_ID_KEY, userId);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] apiKeySecretBytes = secret.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);
        builder.setExpiration(expiration);
        return builder.compact();
    }

    @Override
    public boolean checkToken(String token) {
        // 检查jwt token 是否有效
        if(null == token) return false;
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        Date notBefore = claims.getNotBefore();
        Long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis > expiration.getTime()){
            return false;
        }
        if(currentTimeMillis < notBefore.getTime()){
            return false;
        }
        return true;
    }

    @Override
    public Object getUserId(String token) {
        // 从jwt token中获取用户ID
        if(null == token) return null;
        return getClaimsFromToken(token).get(USER_ID_KEY);
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
        // do nothing for jwt token manager
    }
    private Claims getClaimsFromToken(String token){
        JwtParser jwtParser = Jwts.parser().setSigningKey(secret.getBytes());
        Jws<Claims> parse = jwtParser.parseClaimsJws(token);
        Claims claims = parse.getBody();
        return claims;
    }
}
