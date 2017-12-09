/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.impl;

import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.usermanagementserver.token.JWTUtil;
import com.mycompany.usermanagementserver.token.TokenElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author TuanTV
 */
@Service
public class TokenServiceImpl implements TokenService{
    
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    
    @Override
    public String createToken(String userId) {
        TokenElement tokenElement = new TokenElement(userId);
        String token = JWTUtil.generateToken(tokenElement);
        return token;
    }
    
    @Override
    public String getUserId(String token) {
        TokenElement tokenElement = JWTUtil.parse(token);
        return tokenElement.getUserId();
    }
}
