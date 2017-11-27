/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.base;

import com.mycompany.usermanagementserver.exception.TokenException;
import com.mycompany.usermanagementserver.session.Session;

/**
 *
 * @author tuantran
 */
public interface SessionService {
    
    public boolean checkToken(String token);
    
    public void addSession(Session session);
    
    public void resetTimeAlive(String token);
    
    public Session getSession(String token)  throws TokenException;
    
    public void remove(String token) throws TokenException;
}
