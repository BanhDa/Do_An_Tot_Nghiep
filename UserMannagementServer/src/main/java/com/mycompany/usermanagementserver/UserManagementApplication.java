/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver;

import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.usermanagementserver.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 *
 * @author tuantran
 */

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner{
    
    @Autowired
    private Config config;
    
    @Autowired
    private MongoOperations mongoOperations;
    
    @Autowired 
    private UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        User user = new User();
//        user.setUserId("");
//        user.setEmail("tuan2@nn.nn");
//        user.setPassword("khongco");
//        user.setGender(1);
//        
//        mongoOperations.save(user);
//        System.out.println("" + user);
    }
}
