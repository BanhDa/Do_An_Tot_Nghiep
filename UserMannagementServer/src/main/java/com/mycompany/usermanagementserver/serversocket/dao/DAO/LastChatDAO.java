/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.dao.DAO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mycompany.usermanagementserver.serversocket.dao.DBManagement;
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
                    BasicDBList lastChats = (BasicDBList) obj.get(ChatInfoDBKey.LAST_CHAT.LAST_CHATS);
                    
                    HashMap<String, String> mapLastChat = new HashMap<>();
                    if (lastChats != null && !lastChats.isEmpty()) {
                        for (Object object : lastChats) {
                            BasicDBObject lastChat = (BasicDBObject) object;
                            String friendId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.FRIEND_ID);
                            String messageId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.MESSAGE_ID);
                            mapLastChat.put(friendId, messageId);
                        }
                    }
                    results.put(userId, mapLastChat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                BasicDBList lastChats = (BasicDBList) obj.get(ChatInfoDBKey.LAST_CHAT.LAST_CHATS);
                for (Object object : lastChats) {
                    BasicDBObject lastChat = (BasicDBObject) object;
                    String friendId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.FRIEND_ID);
                    String messageId = lastChat.getString(ChatInfoDBKey.LAST_CHAT.MESSAGE_ID);
                    results.put(friendId, messageId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    public static void update(String userId, String friendId, String messageId) {
        try {
            if (StringUtils.isValid(userId, friendId, messageId)) {
                ObjectId id = new ObjectId(userId);
                BasicDBObject query = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.ID, id);
                BasicDBObject friendIdMatch = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.FRIEND_ID, friendId);
                query.append(ChatInfoDBKey.LAST_CHAT.LAST_CHATS, new BasicDBObject("$elemMatch", friendIdMatch) );
                DBObject findResult = LAST_CHAT_COLLECTION.findOne(query);

                if (findResult != null) {
                    String keyUpdate = ChatInfoDBKey.LAST_CHAT.LAST_CHATS + ".$." + ChatInfoDBKey.LAST_CHAT.MESSAGE_ID;
                    BasicDBObject document = new BasicDBObject(keyUpdate, messageId);
                    BasicDBObject update = new BasicDBObject("$set", document);

                    LAST_CHAT_COLLECTION.update(query, update);
                } else {
                    BasicDBObject updateQuery = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.ID, id);
                    BasicDBObject updateLastChat = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.FRIEND_ID, friendId);
                    updateLastChat.append(ChatInfoDBKey.LAST_CHAT.MESSAGE_ID, messageId);

                    BasicDBObject lastChats = new BasicDBObject(ChatInfoDBKey.LAST_CHAT.LAST_CHATS, updateLastChat);
                    BasicDBObject updateCommand = new BasicDBObject("$push", lastChats);
                    LAST_CHAT_COLLECTION.update(updateQuery, updateCommand, true, false);
                }
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
