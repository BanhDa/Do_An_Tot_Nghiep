/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.client;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 *
 * @author tuantran
 */
public class SocketClient {
    
    public static void main(String[] args) {
        IO.Options options = new IO.Options();
        options.forceNew = true;
        final Socket socket;
        try {
            socket = IO.socket("http://localhost:9092");
            socket.on(Socket.EVENT_CONNECT, (Object... args1) -> {
                System.out.println("connect");
                
            }).on(Socket.EVENT_DISCONNECT, (Object... args1) -> {
                System.out.println("disconnect");
            }).on("chat", (Object... args1) -> {
                System.out.println("receiver data: " + args1[0]);
            });
            socket.connect();
            Thread.sleep(1000);
            socket.emit("chat", "hello server");
            
            Thread.sleep(10000);
            socket.disconnect();
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
