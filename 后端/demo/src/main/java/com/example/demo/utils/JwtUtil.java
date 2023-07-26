package com.example.demo.utils;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    private static long accTime = 1000;
    private static long refTime = 1000*5;
    private static String signature = "admin";

    //生成Token
    public static String createToken(String id,String type){
        JwtBuilder jwtBuilder = Jwts.builder();
        long expiration = accTime;
        if (type.equals("ref"))
            expiration = refTime;
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .setId(id)
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return jwtToken;
    }

    //token解密
    public static String checkToken(String token){
        if(token == null)
            return "error";
        Claims claims ;
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
            claims = claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            return "tokenExpired";
        } catch (Exception e) {
            return "error";
        }
        return claims.getId();

    }
}
