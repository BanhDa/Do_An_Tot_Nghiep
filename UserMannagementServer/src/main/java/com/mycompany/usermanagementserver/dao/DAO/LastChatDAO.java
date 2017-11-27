/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.dao.DAO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mycompany.usermanagementserver.dao.DBManagement;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.webchatutil.constant.mongodbkey.ChatInfoDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;

/**
 *
 * @author tuantran
 */
public class LastChatDAO {
    
    private static final DBCollection LAST_CHAT_COLLECTION = DBManagement.getCHAT_INFO_DB().getCollection(ChatInfoDBKey.LAST_CHAT_COLLECTION_NAME);
    
    public static Map<String, HashMap<String, String>> getAll() {
        Map<String, HashMap<String, String>> results = new HashMap<>();
        
        try {
            try ( DBCursor cursor = LAST_CHAT_COLLECTION.find() ) {
                while (cursor.hasNext()) {
                    BasicDBObject obj = (BasicDBObject) cursor.next();
                    String userId = obj.getObjectId(ChatInfoDBKey.LAST_CHAT.ID).toString();
                    BasicDBList lastChats = (BasicDBList) obj.get(ChatInfoDBKey.LAST_CHAT.LAST_CHAT);
                    
                    HashMap<String, String> mapLastChat = new HashMap<>();
                    for (Object object : lastChats) {
                        BasicDBObject lastChat = (BasicDBObject) object;
                        String fromUserId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.FROM_USERID);
                        String toUserId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.TO_USERID);
                        
                        String friendId = fromUserId.equals(fromUserId) ? toUserId : fromUserId;
                        String messageId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.MESSAGE_ID);
                        mapLastChat.put(friendId, messageId);
                    }
                    
                    results.put(userId, mapLastChat);
                }
            }
        } catch (Exception e) {
        }
        
        return results;
    }
    
    public static Map<String, String> getByUserId(String userId) {
        Map<String, String> results = new HashMap<>();
        
        ObjectId id = new ObjectId(userId);
        BasicDBObject query = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.ID, id);
        
        try {
            BasicDBObject obj = (BasicDBObject) LAST_CHAT_COLLECTION.findOne(query);
            if (obj != null) {
                BasicDBList lastChats = (BasicDBList) obj.get(ChatInfoDBKey.LAST_CHAT.LAST_CHAT);
                for (Object object : lastChats) {
                    BasicDBObject lastChat = (BasicDBObject) object;
                    String fromUserId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.FROM_USERID);
                    String toUserId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.TO_USERID);
                    String messageId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.MESSAGE_ID);
                    if (fromUserId.equals(userId)) {
                        results.put(toUserId, messageId);
                    } else {
                        results.put(fromUserId, messageId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    public static void insert(String userId, Message message) {
        
        if (StringUtils.isValid(userId)) {
            ObjectId id = new ObjectId(userId);
            BasicDBObject query = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.ID, id);
            
            DBObject findResult = LAST_CHAT_COLLECTION.findOne(query);
            BasicDBObject lastChat = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.FROM_USERID, message.getFromUserId())
                    .append(ChatInfoDBKey.LAST_CHAT.TO_USERID, message.getToUserId())
                    .append(ChatInfoDBKey.LAST_CHAT.MESSAGE_ID, message.getId());

            BasicDBList lastChats = new BasicDBList();
            lastChats.add(lastChat);
            
            if (findResult != null) {
                BasicDBObject document = new BasicDBObject();
                document.append(ChatInfoDBKey.LAST_CHAT.ID, new ObjectId(userId));
                document.append(ChatInfoDBKey.LAST_CHAT.LAST_CHAT, lastChats);
                
                LAST_CHAT_COLLECTION.insert(document);
            } else {
                BasicDBObject push = new BasicDBObject("$push", 
                        new BasicDBObject(ChatInfoDBKey.LAST_CHAT.LAST_CHAT, lastChat));
                
                LAST_CHAT_COLLECTION.update(query, push, true, false);
            }
        }   
    }
}
