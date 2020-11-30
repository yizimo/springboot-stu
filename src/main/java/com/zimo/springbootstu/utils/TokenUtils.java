package com.zimo.springbootstu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private static final long TIME = 30*60*1000;
    private static final String TOEKN_KEY = "ZXCASDQWE2020";


    /**
     * 生成token
     * @param username 用户名
     * @param perName 权限名
     * @return
     */
    public static String token(String username, String perName) {

        String token  = "";
        try {
            Date date = new Date(System.currentTimeMillis() + TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOEKN_KEY);
            Map<String, Object> map = new HashMap<>(2);
            map.put("typ","JWT");
            map.put("alg","HS256");
            token = JWT.create()
                    .withHeader(map)
                    .withClaim("username",username)
                    .withClaim("perName",perName)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return token;
    }

    /**
     * 校验token的正确性
     * @param token
     * @return
     */
   public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOEKN_KEY)).build();
            jwt = jwtVerifier.verify(token);
        } catch (Exception e) {
            logger.debug("token 校验失败");
            return null;
        }
        return jwt.getClaims();
   }

    /**
     * 获取token中存入的数据
     * @param token
     * @param name
     * @return
     */
   public static String getInfo(String token, String name) {
        String value = "";
        Map<String, Claim> map = verifyToken(token);
        if(null == map) {
            return value;
        }
        Claim claim = map.get(name);
        logger.info("claim:" + claim.asString());
        return claim.asString();
   }
}
