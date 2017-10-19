/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver.server.service.impl;

import com.mycompany.usermannagementserver.cachemanagement.RedisUtil;
import com.mycompany.usermannagementserver.server.service.base.RedisService;
import com.mycompany.webchatutil.constant.Constant;
import org.springframework.stereotype.Service;

/**
 *
 * @author TuanTV
 */
@Service
public class RedisServiceImpl implements RedisService{
    
    @Override
    public boolean addToken(String userId, String token) {
        
        String key = Constant.STORED_TOKEN_KEY + userId;
        RedisUtil.put(key, token);
        
        return false;
    }
}
