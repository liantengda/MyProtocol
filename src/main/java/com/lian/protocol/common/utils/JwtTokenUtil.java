package com.lian.protocol.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lian.protocol.common.constants.MyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);


    private static final long EXPIRE_TIME = 30 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(MyConstant.JWTSECRET+getId(token));
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的id信息,无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Long getId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("id").asLong();

        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 生成token
     *
     * @param userId  用户id
     * @return 加密的token
     */
    public static String sign(Long userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(MyConstant.JWTSECRET + userId);
            // 附带username信息
            return JWT.create().withClaim("id", userId).withExpiresAt(date).sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    public static void main(String[] args) {
//        String originToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MzI5LCJleHAiOjE1ODc5MDc5NjV9.SC2hqHLcBmsC683LKcUD2RZsdqxS7kxOjFWmnyJM9cQ";
//        String redisToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MzI5LCJleHAiOjE1ODc5MDc5NjV9.SC2hqHLcBmsC683LKcUD2RZsdqxS7kxOjFWmnyJM9cQ";
        String token = sign(16L);
        System.out.println(token);
//        boolean verify = verify(originToken);
//        System.out.println("token是否合法"+verify);
//        System.out.println("新生成的token与实际是否一致"+token.equals(originToken));
//        System.out.println("redis中的token与实际的是否一致"+originToken.equals(redisToken));
//        Long id = getId(originToken);
//        System.out.println("实际上用户id为---->"+id);

//        String YST_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNTU3ODQxNTQ4fQ.R8T63L5Xe4fNH1aXazuU3-e0ENu9nVGye-cYDnz6vsQ";
//        Long id1 = getId(YST_KEY);
//        System.out.println(id1);
    }
}
