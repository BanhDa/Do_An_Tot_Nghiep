/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver.token;

import com.mycompany.usermannagementserver.exception.TokenException;
import com.mycompany.webchatutil.utils.StringUtils;
import com.mycompany.webchatutil.utils.Validator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @author tuantran
 */
public class JWTUtil {
    
    private static final String SIGNKEY = "e20VxCsGziaijkcsn-0Kv0M37hFPbEyCB0UggWAo9rpvaZrFDkFxBYccIpufp9OXoQlPqeJFjjFGbifsm-FsxQ";
    
    public static final String USER_ID = "user_id";
    public static final String ALIVE_TIME = "alive_time";
    
    public static String generateToken(TokenElement tokenElement) throws TokenException{
        if (!Validator.validateObject(tokenElement)) {
            throw new TokenException("Token element null");
        }
        
        Claims claims = Jwts.claims();
        put(claims, USER_ID, tokenElement.getUserId());
        put(claims, ALIVE_TIME, tokenElement.getAliveTime());
        JwtBuilder builder = Jwts.builder();
        builder.setClaims(claims);
        
        return builder.signWith(SignatureAlgorithm.HS512, SIGNKEY).compact();
    }
    
    private static void put(Claims claims, String key, String value) {
        if (StringUtils.isValid(value)) {
            claims.put(key, value);
        }
    }
    
    private static void put(Claims claims, String key, Object value) {
        if (Validator.validateObject(value)) {
            claims.put(key, value);
        }
    }
    
    public static TokenElement parse(String token) throws TokenException{
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(SIGNKEY);
            Claims body = parser.parseClaimsJws(token).getBody();
            
            TokenElement tokenElement = new TokenElement();
            
            Object userId = body.get(USER_ID);
            Object aliveTime = body.get(ALIVE_TIME);
            tokenElement.setUserId(userId.toString());
            tokenElement.setAliveTime(Long.parseLong(aliveTime.toString()));
            return tokenElement;
        } catch (Exception e) {
            throw new TokenException("parse token error", e);
        }
    }
    
}
