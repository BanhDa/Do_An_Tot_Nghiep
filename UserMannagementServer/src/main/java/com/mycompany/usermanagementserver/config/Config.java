/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.config;

import com.mongodb.MongoClient;
import com.mycompany.webchatutil.constant.UserDBKey;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author tuantran
 */
@Configuration
@ComponentScan("com.mycompany.usermanagementserver")
@EnableWebMvc
public class Config {
    
    @Value("${session.timeout}")
    public Long timeout;
    @Value("${session.config}")
    public String config;
    @Value("${mongodb.host}")
    public String mongoHost;
    @Value("${mongodb.port}")
    public Integer mongoPort;
    
    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(mongoHost, mongoPort);
    }
    
    @Bean
    public MongoDbFactory mongoDBFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(mongoClient(), UserDBKey.USER_DB_NAME);
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongoDBFactory());
    }
    public static Long SESSION_TIMEOUT = 60L;
}
