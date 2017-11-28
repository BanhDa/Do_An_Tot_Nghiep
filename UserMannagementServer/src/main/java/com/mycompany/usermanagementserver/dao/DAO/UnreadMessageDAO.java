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
import com.mycompany.webchatutil.constant.mongodbkey.ChatInfoDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;

/**
 *
 * @author tuantran
 */
public class UnreadMessageDAO {
    
    private static final DBCollection UNREAD_MESSAGE_COLLECTION = DBManagement.getCHAT_INFO_DB().getCollection(ChatInfoDBKey.UNREAD_MESSAGE_COLLECTION_NAME);

    public static Map<String, HashMap<String, Integer>> getAll() {
        Map<String, HashMap<String, Integer>> resutls = new HashMap<>();
        
        try {
            try (DBCursor cursor = UNREAD_MESSAGE_COLLECTION.find()) {
                while (cursor.hasNext()) {                    
                    BasicDBObject obj = (BasicDBObject) cursor.next();
                    
                    String userId = obj.getObjectId(ChatInfoDBKey.UNREAD_MESSAGE.ID).toString();
                    BasicDBList unreads = (BasicDBList) obj.get(ChatInfoDBKey.UNREAD_MESSAGE.UNREADS);
                    if (unreads != null && !unreads.isEmpty()) {
                        HashMap<String, Integer> mapUnreads = new HashMap<>();
                        for (Object object : unreads) {
                            BasicDBObject unread = (BasicDBObject) object;
                            String friendId = unread.getString(ChatInfoDBKey.UNREAD_MESSAGE.FRIEND_ID);
                            Integer unreadNumber = unread.getInt(ChatInfoDBKey.UNREAD_MESSAGE.UNREAD_NUMBER);
                            mapUnreads.put(friendId, unreadNumber);
                        }
                        resutls.put(userId, mapUnreads);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resutls;
    }
    
    public static void increase(String userId, String friendId) {
        try {
            if (StringUtils.isValid(userId, friendId)) {
                ObjectId id = new ObjectId(userId);
                BasicDBObject query = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.ID, id);
                BasicDBObject friendIdMatch = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.FRIEND_ID, friendId);
                query.append(ChatInfoDBKey.UNREAD_MESSAGE.UNREADS, new BasicDBObject("$elemMatch", friendIdMatch) );
                DBObject findResult = UNREAD_MESSAGE_COLLECTION.findOne(query);

                if (findResult != null) {
                    String keyUpdate = ChatInfoDBKey.UNREAD_MESSAGE.UNREADS + ".$." + ChatInfoDBKey.UNREAD_MESSAGE.UNREAD_NUMBER;
                    BasicDBObject document = new BasicDBObject(keyUpdate, 1);
                    BasicDBObject update = new BasicDBObject("$inc", document);

                    UNREAD_MESSAGE_COLLECTION.update(query, update);
                } else {
                    BasicDBObject updateQuery = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.ID, id);
                    BasicDBObject updateUnread = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.FRIEND_ID, friendId);
                        updateUnread.append(ChatInfoDBKey.UNREAD_MESSAGE.UNREAD_NUMBER, 1);

                    BasicDBObject unreads = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.UNREADS, updateUnread);
                    BasicDBObject updateCommand = new BasicDBObject("$push", unreads);
                    UNREAD_MESSAGE_COLLECTION.update(updateQuery, updateCommand, true, false);
                }
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void remove(String userId, String friendId) {
        if (StringUtils.isValid(userId, friendId)) {
            ObjectId id = new ObjectId(userId);
            BasicDBObject query = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.ID, id);
            
            BasicDBObject friendObj = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.FRIEND_ID, friendId);
            BasicDBObject unreads = new BasicDBObject(ChatInfoDBKey.UNREAD_MESSAGE.UNREADS, friendObj);
            BasicDBObject update = new BasicDBObject("$pull", unreads);
            
            try {
                UNREAD_MESSAGE_COLLECTION.update(query, update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
