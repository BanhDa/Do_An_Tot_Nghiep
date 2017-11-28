/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.constant.mongodbkey;

/**
 *
 * @author tuantran
 */
public class ChatInfoDBKey {
    
    public static final String DB_NAME = "chatinfodb";
    
    public static final String LAST_CHAT_COLLECTION_NAME = "last_chat";
    public static class LAST_CHAT {
        public static final String ID = "_id";
        public static final String LAST_CHATS = "last_chat";
        
        //list
        public static final String FRIEND_ID = "friend_id";
        public static final String MESSAGE_ID = "message_id";
    }
    
    public static final String UNREAD_MESSAGE_COLLECTION_NAME = "unread_message";
    public static class UNREAD_MESSAGE {
        public static final String ID = "_id";
        public static final String UNREADS = "unread";
        
        //list
        public static final String FRIEND_ID = "friend_id";
        public static final String UNREAD_NUMBER = "unread_number";
    }
}
