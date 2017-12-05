/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.worker;

import com.mycompany.usermanagementserver.entity.Socket;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.serversocket.dao.DAO.ChatLogDAO;
import com.mycompany.usermanagementserver.serversocket.dao.DAO.UnreadMessageDAO;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnection;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnectionsManagement;
import com.mycompany.usermanagementserver.unreadmessage.UnreadMessageManagement;
import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author tuantran
 */
public class EventReadExecutor extends Executor{
    
    public EventReadExecutor(UserConnection userConnection, Message message) {
        super(userConnection, message);
    }
    
    @Override
    public void run() {
        try {
            sendMessage(userConnection, message);
            removeUnreadMessageNumber();
            updateReadTimeToDB();
        } catch (Exception ex) {
            System.out.println("process message read error");
            System.out.println("user: " + userConnection.getUserId());
            System.out.println("message: " + message);
            ex.printStackTrace();
        }
    }
    
    private void removeUnreadMessageNumber() {
        UnreadMessageManagement.read(message.getFromUserId(), message.getToUserId());
        UnreadMessageDAO.remove(message.getFromUserId(), message.getToUserId());
    }
    
    private void updateReadTimeToDB() {
        ChatLogDAO.updateReadTime(message.getFromUserId(), message.getToUserId());
    }
    
    @Override
    protected void sendMessage(String userId, Message message) {
        Collection<UserConnection> userConnections = UserConnectionsManagement.getAllConnectionOfUser(userId);
        if (userConnections != null && !userConnections.isEmpty()) {
            for (UserConnection userConnect : userConnections) {
                userConnect.getSocket().sendEvent(Socket.EVENT_READED_MESSAGE, message);
            }
        }
    }
    
    @Override
    protected void broadcast(UserConnection userConnection, Message message) {
        Collection<UserConnection> userConnections = UserConnectionsManagement.getAllConnectionOfUser( userConnection.getUserId() );
        UUID sessionConnection = userConnection.getSocket().getSessionId();
        if (userConnections != null && !userConnections.isEmpty()) {
            for (UserConnection userConnect : userConnections) {
                if ( !sessionConnection.equals( userConnect.getSocket().getSessionId() )) {
                    userConnect.getSocket().sendEvent(Socket.EVENT_READED_MESSAGE, message);
                }
            }
        }
    }
}
