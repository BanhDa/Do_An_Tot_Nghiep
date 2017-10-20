/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver.server.service.impl;

import com.mycompany.usermannagementserver.server.service.base.TokenService;
import com.mycompany.usermannagementserver.token.JWTUtil;
import com.mycompany.usermannagementserver.token.TokenElement;
import org.springframework.stereotype.Service;

/**
 *
 * @author TuanTV
 */
@Service
public class TokenServiceImpl implements TokenService{
    
    @Override
    public String createToken(String userId) {
        TokenElement tokenElement = new TokenElement(userId);
        String token = JWTUtil.generateToken(tokenElement);
        
        return token;
    }
    
}
