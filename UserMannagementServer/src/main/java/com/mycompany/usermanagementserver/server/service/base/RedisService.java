/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.base;

/**
 *
 * @author TuanTV
 */
public interface RedisService {
    
    public boolean addToken(String userId, String token);
}