/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mycompany.webchatutil.constant.mongodbkey.ChatInfoDBKey;

/**
 *
 * @author tuantran
 */
public class DBManagement {

    private static MongoClient mongoClient;
    private static DB CHAT_INFO_DB;
    
    static {
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
        mongoClient = new MongoClient(serverAddress);
        
        CHAT_INFO_DB = mongoClient.getDB(ChatInfoDBKey.DB_NAME);
    }

    public static MongoClient getConnect() {
        return mongoClient;
    }

    public static DB getCHAT_INFO_DB() {
        return CHAT_INFO_DB;
    }
    
}
