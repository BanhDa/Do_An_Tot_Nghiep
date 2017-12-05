/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.worker;

import com.mycompany.usermanagementserver.entity.Socket;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.lastchat.LastChatManagement;
import com.mycompany.usermanagementserver.serversocket.log.LogContainer;
import com.mycompany.usermanagementserver.serversocket.log.LogLastChatExecutor;
import com.mycompany.usermanagementserver.serversocket.log.LogMessageChatExecutor;
import com.mycompany.usermanagementserver.serversocket.log.LogUnreadMessageExecutor;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnection;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnectionsManagement;
import com.mycompany.usermanagementserver.unreadmessage.UnreadMessageManagement;
import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author tuantran
 */
public class EventChatExecutor extends Executor{
    
    public EventChatExecutor(UserConnection userConnection, Message message) {
        super(userConnection, message);
    }
    
    @Override
    public void run() {
        try {
            sendMessage(userConnection, message);
            increaseUnreadMessage(message.getFromUserId(), message.getToUserId());
            pushLastChat(message.getFromUserId(), message.getToUserId(), message.getMessageId());
            logToDB(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void increaseUnreadMessage(String fromUserId, String toUserId) {
        UnreadMessageManagement.increase(toUserId, fromUserId);
        
        LogUnreadMessageExecutor logUnreadTask = new LogUnreadMessageExecutor(toUserId, fromUserId);
        LogContainer.addTask(logUnreadTask);
    }
    
    private void pushLastChat(String userId, String friendId, String messageId) {
        LastChatManagement.putLastChat(userId, friendId, messageId);
        LastChatManagement.putLastChat(friendId, userId, messageId);
        
        LogLastChatExecutor lastChatFromUser = new LogLastChatExecutor(userId, friendId, messageId);
        LogContainer.addTask(lastChatFromUser);
        LogLastChatExecutor lastChatToUser = new LogLastChatExecutor(friendId, userId, messageId);
        LogContainer.addTask(lastChatToUser);;
    }
    
    private void logToDB(Message message) {
        LogMessageChatExecutor task = new LogMessageChatExecutor(message);
        LogContainer.addTask(task);
    }
}
