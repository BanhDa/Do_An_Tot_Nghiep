/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver;

import com.mycompany.usermannagementserver.cachemanagement.RedisUtil;
import com.mycompany.usermannagementserver.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author tuantran
 */

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner{
    
    @Autowired
    private Config config;
    
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        RedisUtil.ping();
    }
}
