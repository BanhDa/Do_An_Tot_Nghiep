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
public interface TokenService {
    
    public String createToken(String userId);
    
    public String getUserId(String token);
}
