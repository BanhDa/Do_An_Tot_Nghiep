/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket;

import com.corundumstudio.socketio.AckRequest; 
import com.corundumstudio.socketio.Configuration; 
import com.corundumstudio.socketio.SocketIOClient; 
import com.corundumstudio.socketio.SocketIOServer; 
import com.mycompany.usermanagementserver.entity.Socket;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.server.common.Helper;
import com.mycompany.usermanagementserver.server.request.MessageRequest;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnection;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnectionsManagement;
import com.mycompany.usermanagementserver.serversocket.worker.EventChatContainer;
import com.mycompany.usermanagementserver.serversocket.worker.EventChatExecutor;
import com.mycompany.usermanagementserver.token.JWTUtil;
import com.mycompany.webchatutil.utils.ModelMapperUtils;
import com.mycompany.webchatutil.utils.StringUtils;

/**
 *
 * @author tuantran
 */
public class EventChatLauncher {
    
    public static void start() throws InterruptedException { 
 
        Configuration config = new Configuration(); 
        config.setHostname("localhost"); 
        config.setPort(9092); 
 
        final SocketIOServer server = new SocketIOServer(config); 
        
        server.addEventListener(Socket.EVENT_CONNECTION, String.class, ((SocketIOClient socketIOClient, String token, AckRequest ackRequest) -> {
            try {
                if (StringUtils.isValid(token) && Helper.checkToken(token)) {
                    String userId = JWTUtil.getUserId( token );
                    if (!UserConnectionsManagement.existsConnection(userId, socketIOClient.getSessionId())) {
                        UserConnection userConnection = new UserConnection(userId, socketIOClient);
                        UserConnectionsManagement.addConnection(userConnection);
                    }
                } else {
                    //send connection error
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));

        server.addEventListener(Socket.EVENT_CHAT, MessageRequest.class, (SocketIOClient socketIOClient, MessageRequest data, AckRequest ackRequest) -> {

            if (Helper.checkToken(data.getToken())) {
                String userId = JWTUtil.getUserId( data.getToken() );
                UserConnection userConnection = new UserConnection(userId, socketIOClient);
                if (!UserConnectionsManagement.existsConnection(userId, socketIOClient.getSessionId())) {
                    UserConnectionsManagement.addConnection(userConnection);
                }
                Message message = ModelMapperUtils.toObject(data, Message.class);
                EventChatExecutor executor = new EventChatExecutor(userConnection, message);
                EventChatContainer.addTask(executor);
            } else {
                //send connection error
            }
            
        }); 
 
        server.start(); 
 
        Thread.sleep(Integer.MAX_VALUE); 
 
        server.stop(); 
    } 
    
}
