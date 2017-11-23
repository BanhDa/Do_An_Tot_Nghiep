/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository.common;

import com.mongodb.BasicDBObject;
import com.mycompany.webchatutil.utils.StringUtils;

/**
 *
 * @author tuantran
 */
public class MongoHelper {
    
    public static void put(BasicDBObject obj, String key, Object value) {
        if (StringUtils.isValid(key) && value != null) {
            obj.append(key, value);        
        }
    }
    
}
