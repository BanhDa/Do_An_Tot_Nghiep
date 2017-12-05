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
public class StaticFileDBKey {
    
    public static final String DB_NAME = "staticfiledb";
    
    public static final String IMAGE_COLLECTION_NAME = "image";
    public static class IMAGE {
        public static final String ID = "_id";
        public static final String USER_ID = "user_id";
        public static final String PATH = "path";
        public static final String UPLOAD_TIME = "upload_time";
        public static final String IS_AVATAR = "is_avatar";
    }
}
