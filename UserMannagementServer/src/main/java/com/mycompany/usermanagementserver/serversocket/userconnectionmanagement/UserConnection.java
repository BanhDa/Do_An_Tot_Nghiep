/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.userconnectionmanagement;

import com.corundumstudio.socketio.SocketIOClient;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author tuantran
 */
public class UserConnection {
    
    private String userId;
    private SocketIOClient socket;
    private Long timeAlive;

    public UserConnection(String userId, SocketIOClient socket) {
        this.userId = userId;
        this.socket = socket;
        this.timeAlive = System.currentTimeMillis();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SocketIOClient getSocket() {
        return socket;
    }

    public void setSocket(SocketIOClient socket) {
        this.socket = socket;
    }

    public Long getTimeAlive() {
        return timeAlive;
    }

    public void setTimeAlive(Long timeAlive) {
        this.timeAlive = timeAlive;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.socket);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserConnection other = (UserConnection) obj;
        UUID uuid = this.socket.getSessionId();
        UUID otherUuid = other.getSocket().getSessionId();
        return uuid.equals(otherUuid);
    }
    
}
