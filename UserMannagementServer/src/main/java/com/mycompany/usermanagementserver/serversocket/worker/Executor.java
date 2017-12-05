/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.worker;

import com.mycompany.usermanagementserver.entity.Socket;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnection;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnectionsManagement;
import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author tuantran
 */
public abstract class Executor implements Runnable{
    protected UserConnection userConnection;
    protected Message message;

    public Executor(UserConnection userConnection, Message message) {
        this.userConnection = userConnection;
        this.message = message;
    }
    
    protected void sendMessage(UserConnection userConnection, Message message) {
        broadcast(userConnection, message);
        sendMessage(message.getToUserId(), message);
    }
    
    protected void sendMessage(String userId, Message message) {
        Collection<UserConnection> userConnections = UserConnectionsManagement.getAllConnectionOfUser(userId);
        if (userConnections != null && !userConnections.isEmpty()) {
            for (UserConnection userConnect : userConnections) {
                userConnect.getSocket().sendEvent(Socket.EVENT_CHAT, message);
            }
        }
    }
    
    protected void broadcast(UserConnection userConnection, Message message) {
        Collection<UserConnection> userConnections = UserConnectionsManagement.getAllConnectionOfUser( userConnection.getUserId() );
        UUID sessionConnection = userConnection.getSocket().getSessionId();
        if (userConnections != null && !userConnections.isEmpty()) {
            for (UserConnection userConnect : userConnections) {
                if ( !sessionConnection.equals( userConnect.getSocket().getSessionId() )) {
                    userConnect.getSocket().sendEvent(Socket.EVENT_CHAT, message);
                }
            }
        }
    }
}
