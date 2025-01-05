package com.happycode.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);  // 签名密钥
    private static final long EXPIRATION_TIME = 1000 * 60 * 30 * 1;  // 24小时过期时间

    // 生成 JWT Token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // 设置 Token 的用户名
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 设置过期时间
                .signWith(SECRET_KEY)  // 使用生成的密钥签名
                .compact();
    }

    // 解析 Token，获取其 Claims 部分
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // 设置密钥
                .build()
                .parseClaimsJws(token)  // 解析 Token
                .getBody();  // 返回 Token 的负载部分（Claims）
    }

    // 验证 Token 是否过期
    public static boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration().before(new Date());  // 判断过期时间是否在当前时间之前
    }

    // 从 Token 中获取用户名
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();  // 返回 Token 中的用户名
    }
}
