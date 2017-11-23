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
import com.mycompany.usermanagementserver.entity.Event;
import io.socket.client.Socket;
import org.apache.tomcat.util.net.SocketEvent;

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
        server.addEventListener("conn", String.class, ((SocketIOClient socketIOClient, String data, AckRequest ackRequest) -> {
            System.out.println("connect");
        }));
        server.addEventListener("disconn", Object.class, ((SocketIOClient socketIOClient, Object data, AckRequest ackRequest) -> {
            System.out.println("connected");
        }));
        server.addEventListener("connection", Object.class, ((SocketIOClient socketIOClient, Object data, AckRequest ackRequest) -> {
            System.out.println("connection");
        }));
        server.addEventListener(Event.CHAT, String.class, (SocketIOClient socketIOClient, String data, AckRequest ackRequest) -> {
                System.out.println("data" + data);
                socketIOClient.sendEvent(Event.CHAT, "heello");
        }); 
 
        server.start(); 
 
        Thread.sleep(Integer.MAX_VALUE); 
 
        server.stop(); 
    } 
    
    public static void main(String[] srgs) {
        try {
            start();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
