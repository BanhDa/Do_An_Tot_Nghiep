/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.dao.DAO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mycompany.usermanagementserver.serversocket.dao.DBManagement;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.server.repository.common.MongoHelper;
import com.mycompany.webchatutil.constant.mongodbkey.ChatLogDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.Date;
import org.bson.types.ObjectId;

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
    
    public static void updateReadTime(String userId, String friendId) {
        try {
            if (!StringUtils.isValid(userId, friendId)) {
                return;
            }
            
            DBCollection userCollection = getCollection(userId);
            DBCollection friendCollection = getCollection(friendId);
            
            BasicDBObject query = new BasicDBObject();
            
            BasicDBObject notExists = new BasicDBObject("$exists", false);
            query.append(ChatLogDBKey.READ_TIME, notExists);
            
            Date now = new Date();
            BasicDBObject readTime = new BasicDBObject(ChatLogDBKey.READ_TIME, now.toString());
            BasicDBObject setReadTime = new BasicDBObject("$set", readTime);
            if (userCollection != null) {
                BasicDBList list = new BasicDBList();
                BasicDBObject friendIsSender = new BasicDBObject(ChatLogDBKey.FROM_USER_ID, friendId);
                list.add(friendIsSender);
                BasicDBObject friendIsReceiver = new BasicDBObject(ChatLogDBKey.TO_USER_ID, friendId);
                list.add(friendIsReceiver);
                query.append("$or", list);
                userCollection.update(query, setReadTime, false, true);
            }
            if (friendCollection != null) {
                BasicDBList list = new BasicDBList();
                BasicDBObject userIsSender = new BasicDBObject(ChatLogDBKey.FROM_USER_ID, userId);
                list.add(userIsSender);
                BasicDBObject userIsReceiver = new BasicDBObject(ChatLogDBKey.TO_USER_ID, userId);
                list.add(userIsReceiver);
                query.append("$or", list);
                friendCollection.update(query, setReadTime, false, true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static Message parseToObject(BasicDBObject obj) {
        Message message = null;
        try {
            message = new Message();
            message.setId( obj.getObjectId(ChatLogDBKey.ID).toString() );
            message.setMessageId( obj.getString(ChatLogDBKey.MESSAGE_ID) );
            message.setFromUserId( obj.getString(ChatLogDBKey.FROM_USER_ID) );
            message.setToUserId( obj.getString(ChatLogDBKey.TO_USER_ID) );
            message.setMessageType( obj.getString( ChatLogDBKey.MESSAGE_TYPE) );
            message.setValue( obj.getString(ChatLogDBKey.VALUE) );
            message.setTime( obj.getLong(ChatLogDBKey.TIME) );
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
            if (StringUtils.isValid( message.getId() )) {
                ObjectId id = new ObjectId(message.getId());
                MongoHelper.put(result, ChatLogDBKey.ID, id);
            }
            MongoHelper.put(result, ChatLogDBKey.MESSAGE_ID, message.getMessageId());
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
