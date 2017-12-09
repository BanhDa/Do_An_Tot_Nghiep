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
import com.mycompany.webchatutil.config.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author tuantran
 */

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner{
    private static final Logger logger = LoggerFactory.getLogger(UserManagementApplication.class);
    public static final String CONFIG_FILE = "src\\main\\resources\\application.properties";
    
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("init config");
        CommonConfig.initConfig(CONFIG_FILE);
        cache();
        gabarge();
        EventChatLauncher.start();
    }
    
    private void cache() {
        logger.debug("cache last chat");
        LastChatManagement.cache();
        logger.debug("cache unread");
        UnreadMessageManagement.cache();
    }
    
    private void gabarge() {
        SessionGabarger.start();
        UserConnectionGabarger.start();
    }
}
