/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.session;

/**
 *
 * @author tuantran
 */
public class Session {
    
    private String token;
    private Long timeAlive;

    public Session() {}

    public Session(String token) {
        this.token = token;
        this.timeAlive = System.currentTimeMillis();
    }
    
    public Session(String token, Long timeAlive) {
        this.token = token;
        this.timeAlive = timeAlive;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Long getTimeAlive() {
        return timeAlive;
    }

    public void setTimeAlive(Long timeAlive) {
        this.timeAlive = timeAlive;
    }

    @Override
    public String toString() {
        return "Session{" + "token=" + token + ", timeAlive=" + timeAlive + '}';
    }

}
