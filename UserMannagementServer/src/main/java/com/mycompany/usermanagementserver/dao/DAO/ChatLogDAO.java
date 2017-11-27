/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.dao.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mycompany.usermanagementserver.dao.DBManagement;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.server.repository.common.MongoHelper;
import com.mycompany.webchatutil.constant.mongodbkey.ChatLogDBKey;

/**
 *
 * @author tuantran
 */
public class ChatLogDAO {
    
    private static DB DB;
    
    static {
        DB = DBManagement.getConnect().getDB("chatlogdb");
    }
    
    private static DBCollection getCollection(String userId) {
        return DB.getCollection(userId);
    }
    
    public static void log(Message message) {
        try {
            DBCollection toUserCollection = getCollection(message.getToUserId());
            DBCollection fromUserCollection = getCollection(message.getFromUserId());
            
            DBObject documents = parseTODBObject(message);
            
            fromUserCollection.insert(documents);
            toUserCollection.insert(documents);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static Message parseToObject(BasicDBObject obj) {
        Message message = null;
        try {
            message = new Message();
            message.setId( obj.getObjectId(ChatLogDBKey.ID).toString() );
            message.setFromUserId( obj.getString(ChatLogDBKey.FROM_USER_ID) );
            message.setToUserId( obj.getString(ChatLogDBKey.TO_USER_ID) );
            message.setMessageType( obj.getString( ChatLogDBKey.MESSAGE_TYPE) );
            message.setValue( obj.getString(ChatLogDBKey.VALUE) );
            message.setTime( obj.getString(ChatLogDBKey.TIME) );
            message.setReadTime( obj.getString(ChatLogDBKey.READ_TIME) );
        } catch (Exception ex) {
            System.out.println("parse message error");
            System.out.println(obj);
            ex.printStackTrace();
        }
        return message;
    }
    
    private static DBObject parseTODBObject(Message message) {
        BasicDBObject result = new BasicDBObject();
        
        if (message != null) {
            MongoHelper.put(result, ChatLogDBKey.FROM_USER_ID, message.getFromUserId());
            MongoHelper.put(result, ChatLogDBKey.TO_USER_ID, message.getToUserId());
            MongoHelper.put(result, ChatLogDBKey.MESSAGE_TYPE, message.getMessageType());
            MongoHelper.put(result, ChatLogDBKey.VALUE, message.getValue());
            MongoHelper.put(result, ChatLogDBKey.TIME, message.getTime());
            MongoHelper.put(result, ChatLogDBKey.READ_TIME, message.getReadTime());
        }
        
        return result;
    }
}
