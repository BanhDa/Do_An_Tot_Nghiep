/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.impl;

import com.mycompany.usermanagementserver.cachemanagement.RedisUtil;
import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.usermanagementserver.exception.RedisException;
import com.mycompany.usermanagementserver.exception.TokenException;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.session.Session;
import com.mycompany.webchatutil.constant.Constant;
import com.mycompany.webchatutil.constant.ResponseCode;
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
        try {
            RedisUtil.set(session.getToken(), session.getTimeAlive().toString());
        } catch (RedisException e) {
            System.out.println("add session fail!");
            System.out.println("session : " + session);
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void resetTimeAlive(String token) {
        try {
            Long currentTime = System.currentTimeMillis();
            RedisUtil.set(token, currentTime.toString());
        } catch (RedisException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public Session getSession(String token) throws TokenException{
        try {
            String timeAlive = RedisUtil.get(token);
            if (timeAlive != null) {
                throw new TokenException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
            } else {
                return new Session(token, new Long(timeAlive));
            }
        } catch (RedisException | TokenException | NumberFormatException e) {
            e.printStackTrace();
            throw new TokenException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
        }
    }
    
    @Override
    public boolean checkSession(Session session) throws TokenException{
        Boolean result = false;
        if (session == null || session.getToken() == null || session.getTimeAlive() == null) {
            throw new TokenException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
        }
        long duration = System.currentTimeMillis() - session.getTimeAlive();
        if (duration > SESSION_TIMEOUT) {
            try {
                RedisUtil.remove(session.getToken());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new TokenException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
        } else {
            result = true;
        }
        return result;
    }
}
