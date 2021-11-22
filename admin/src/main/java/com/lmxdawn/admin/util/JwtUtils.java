package com.lmxdawn.admin.util;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * jwt
 */
public class JwtUtils {

    /**
     * 生成 token
     *
     * @param claims    自定义的 map
     * @param ttl 过期时间
     * @return
     */
    public static String createToken(Map<String,Object> claims, Long ttl) {
        return createToken(claims, getKey(), ttl);
    }

    /**
     * 生成 token
     *
     * @param claims    自定义的 map
     * @param stringKey    key
     * @param ttl 过期时间
     * @return
     */
    public static String createToken(Map<String,Object> claims, String stringKey, Long ttl) {
        Key key = generateKey(stringKey);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Long nowMillis = System.currentTimeMillis(); //生成JWT的时间
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT") //设置header
                .setHeaderParam("alg", "HS256")
                .setClaims(claims) //设置payload的键值对
                .signWith(signatureAlgorithm, key); //签名，需要算法和key
        if (ttl != null && ttl >= 0) {
            Long expMillis = nowMillis + ttl * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp); //设置过期时间
        }
        return builder.compact();
    }

    /**
     * 生成 token ，没有过期时间
     *
     * @param claims 自定义的 map
     * @return
     */
    public static String createToken(Map<String,Object> claims) {
        return createToken(claims, null);
    }

    /**
     * 解密 jwt
     * @param jwt 创建的 jwt 字符串
     * @return
     */
    public static Claims parse(String jwt) {
        return parse(jwt, getKey());
    }

    /**
     * 解密 jwt
     * @param jwt 创建的 jwt 字符串
     * @param stringKey key
     * @return
     */
    public static Claims parse(String jwt, String stringKey) {

        if (jwt == null) {
            return null;
        }
        try {
            return Jwts.parser()
                    .setSigningKey(generateKey(stringKey))     //此处的key要与之前创建的key一致
                    .parseClaimsJws(jwt)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    private static String getKey() {
        return  "Zk566dTntkHteEYpA3B4D021DYzpMHc2";
    }

    /**
     * 获取 key
     * @param stringKey key
     * @return
     */
    private static SecretKey generateKey(String stringKey) {
        byte[] encodedKey = Base64.getEncoder().encodeToString(stringKey.getBytes(StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
