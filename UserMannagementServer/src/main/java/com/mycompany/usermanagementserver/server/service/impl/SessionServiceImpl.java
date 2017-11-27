/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.impl;

import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.session.Session;
import com.mycompany.usermanagementserver.session.SessionManagement;
import com.mycompany.webchatutil.constant.Constant;
import com.mycompany.webchatutil.utils.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author tuantran
 */
@Service
public class SessionServiceImpl implements SessionService{
    
    private static final Long SESSION_TIMEOUT = Config.SESSION_TIMEOUT * Constant.A_MINUTE;
    
    @Override
    public void addSession(Session session) {
        SessionManagement.addSession(session);
    }
    
    @Override
    public void resetTimeAlive(String token) {
        if (StringUtils.isValid(token)) {
            SessionManagement.resetSession(token);
        }
    }
    
    @Override
    public Session getSession(String token) {
        Session session = null;
        if (StringUtils.isValid(token)) {
            session = SessionManagement.getSession(token);
        }
        
        return session;
    }
    
    private boolean checkSession(Session session) {
        Boolean result = false;
        if (session == null || session.getToken() == null || session.getTimeAlive() == null) {
            return result;
        }
        long duration = System.currentTimeMillis() - session.getTimeAlive();
        if (duration > SESSION_TIMEOUT) {
            SessionManagement.remove(session.getToken());
        } else {
            result = true;
        }
        return result;
    }

    @Override
    public void remove(String token){
        if (StringUtils.isValid(token)) {
            SessionManagement.remove(token);
        }
    }

    @Override
    public boolean checkToken(String token) {
        Session session = getSession(token);
        if (!checkSession(session)) {
            return false;
        }
        resetTimeAlive(token);
        return true;
    }
}
