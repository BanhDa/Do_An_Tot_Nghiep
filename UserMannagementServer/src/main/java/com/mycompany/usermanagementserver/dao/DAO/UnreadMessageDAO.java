/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.dao.DAO;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mycompany.usermanagementserver.dao.DBManagement;
import com.mycompany.webchatutil.constant.mongodbkey.ChatInfoDBKey;
import java.util.HashMap;
import java.util.Map;

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
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resutls;
    }
    
}
