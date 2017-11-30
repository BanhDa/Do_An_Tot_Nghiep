/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.server.repository.ChatLogRepository;
import com.mycompany.usermanagementserver.server.repository.common.MongoHelper;
import com.mycompany.webchatutil.constant.mongodbkey.ChatLogDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuantran
 */
@Repository
public class ChatLogRepositoryImpl implements ChatLogRepository{
    
    private MongoOperations mongoOperations;
    
    @Autowired
    public ChatLogRepositoryImpl(MongoClient mongoClient) {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, ChatLogDBKey.DB_NAME);
        mongoOperations = new MongoTemplate(factory);
    }

    @Override
    public Message getMessage(String userId, String messageId) {
        Message result = null;
        
        try {
            if (StringUtils.isValid(userId, messageId)) {
                DBCollection collection = getCollection(userId);
                BasicDBObject query = new BasicDBObject(ChatLogDBKey.MESSAGE_ID, messageId);
                BasicDBObject obj = (BasicDBObject) collection.findOne(query);
                if (obj != null) {
                    result = parseToObject(obj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }

    @Override
    public List<Message> getMessages(String userId, Collection<String> messageIds) {
        List<Message> messages = new ArrayList<>();
        
        try {
            if (messageIds != null && !messageIds.isEmpty()) {
                DBCollection collection = getCollection(userId);
                
                DBObject query = QueryBuilder.start(ChatLogDBKey.MESSAGE_ID).in(messageIds).get();
                BasicDBObject sortByTime = new BasicDBObject(ChatLogDBKey.TIME, -1);
                try (DBCursor cursor = collection.find(query).sort(sortByTime) ) {
                    while (cursor.hasNext()) {                        
                        BasicDBObject obj = (BasicDBObject) cursor.next();
                        Message message = parseToObject(obj);
                        if (message != null) {
                            messages.add(message);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return messages;
    }

    @Override
    public List<Message> getMessages(String userId, String friendId, Integer skip, Integer take) {
        List<Message> results = new ArrayList<>();
        
        DBCollection collection = getCollection(userId);
        
        BasicDBList list = new BasicDBList();
        BasicDBObject friendIsSender = new BasicDBObject(ChatLogDBKey.FROM_USER_ID, friendId);
        list.add(friendIsSender);
        BasicDBObject friendIdReceiver = new BasicDBObject(ChatLogDBKey.TO_USER_ID, friendId);
        list.add(friendIdReceiver);
        
        BasicDBObject query = new BasicDBObject("$or", list);
        BasicDBObject sortBySendTime = new BasicDBObject(ChatLogDBKey.TIME, 1);
        
        try {
            DBCursor cursor = collection.find(query).sort(sortBySendTime);
            if (skip != null && take != null) {
                cursor = cursor.skip(skip).limit(take);
            }
            
            while (cursor.hasNext()) {                
                BasicDBObject obj = (BasicDBObject) cursor.next();
                Message message = parseToObject(obj);
                if (message != null) {
                    results.add(message);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return results;
    }
    
    private DBCollection getCollection(String userId) {
        return mongoOperations.getCollection(userId);
    }
    
    private Message parseToObject(BasicDBObject obj) {
        Message message = null;
        try {
            message = new Message();
            message.setId( obj.getObjectId(ChatLogDBKey.ID).toString() );
            message.setMessageId( obj.getString(ChatLogDBKey.MESSAGE_ID) );
            message.setFromUserId( obj.getString(ChatLogDBKey.FROM_USER_ID) );
            message.setToUserId( obj.getString(ChatLogDBKey.TO_USER_ID) );
            message.setValue( obj.getString(ChatLogDBKey.VALUE) );
            message.setMessageType( obj.getString( ChatLogDBKey.MESSAGE_TYPE) );
            message.setTime( obj.getString(ChatLogDBKey.TIME) );
            message.setReadTime( obj.getString(ChatLogDBKey.READ_TIME) );
        } catch (Exception ex) {
            System.out.println("parse message error");
            System.out.println(obj);
            ex.printStackTrace();
        }
        return message;
    }
    
    private DBObject parseTODBObject(Message message) {
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
