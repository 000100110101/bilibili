package com.xxx.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xxx.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    //过期时间设置(1天)
    private static final long Refresh_EXPIRE_TIME = 24*60*60*1000;

    //有效期1小时
    private static final long EXPIRE_TIME = 60*60*1000;
    //私钥设置
    private static final String TOKEN_SECRET = "5xcJVrXNyQDIxK1l2RS9nw";

    public static String getToken(String username,Long time){
        //过期时间和加密算法设置
        Date date=new Date(System.currentTimeMillis() + time);
        Algorithm algorithm =Algorithm.HMAC256(TOKEN_SECRET);
        //头部信息
        Map<String,Object> header=new HashMap<>(2);
        header.put("typ","JWT");
        header.put("alg","HS256");

        return JWT.create()
                .withHeader(header)
                .withExpiresAt(date)
                .withClaim("email",username)
                .sign(algorithm);
    }

    public static String getLoginToken(String username){
        return getToken(username,EXPIRE_TIME);
    }

    public static String getRefreshToken(String username){
        return getToken(username,Refresh_EXPIRE_TIME);
    }


    //获取token用户名
    public static String getUsername(String token){
            Algorithm algorithm =Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt.getClaim("email").asString();
    }

}
