/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver;

import com.mycompany.usermanagementserver.lastchat.LastChatManagement;
import com.mycompany.usermanagementserver.serversocket.EventChatLauncher;
import com.mycompany.usermanagementserver.serversocket.userconnectionmanagement.UserConnectionGabarger;
import com.mycompany.usermanagementserver.session.SessionGabarger;
import com.mycompany.usermanagementserver.unreadmessage.UnreadMessageManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author tuantran
 */

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner{
    
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        cache();
        gabarge();
        EventChatLauncher.start();
    }
    
    private void cache() {
        LastChatManagement.cache();
        UnreadMessageManagement.cache();
    }
    
    private void gabarge() {
        SessionGabarger.start();
        UserConnectionGabarger.start();
    }
}
