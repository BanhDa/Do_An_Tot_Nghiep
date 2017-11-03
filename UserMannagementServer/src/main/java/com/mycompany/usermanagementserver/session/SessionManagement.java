/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.session;

import com.mycompany.usermanagementserver.exception.TokenException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tuantran
 */
public class SessionManagement {
    
    private static ConcurrentHashMap<String, Session> SESSION_CONTAINER = new ConcurrentHashMap<>();
    
    public static void addSession(Session session) {
        if (session != null) {
            SESSION_CONTAINER.put(session.getToken(), session);
        }
    }
    
    public static Session getSession(String token) {
        return SESSION_CONTAINER.get(token);
    }
    
    public static void resetSession(String token) {
        Session session = SESSION_CONTAINER.get(token);
        if (session == null) {
            throw new TokenException("Not session!");
        }
        session.setTimeAlive(System.currentTimeMillis());
        SESSION_CONTAINER.put(token, session);
    }
    
}
