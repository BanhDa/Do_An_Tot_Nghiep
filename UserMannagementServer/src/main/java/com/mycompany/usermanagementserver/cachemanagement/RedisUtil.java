/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.cachemanagement;

import com.mycompany.usermanagementserver.exception.RedisException;
import com.mycompany.webchatutil.utils.StringUtils;
import com.mycompany.webchatutil.utils.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;

/**
 *
 * @author tuantran
 */
public class RedisUtil {
    
    private static Jedis jedis;
    
    static {
        try {
            jedis = new Jedis("localhost", 6379);
        } catch (Exception ex) {
        }
    }
    
    public static Jedis getInstance() {
        return jedis;
    }
    
    public static void ping() {
        System.out.println("jedis ping : " + jedis.ping());
    }
    
    public static void put(String key, String value) throws RedisException{
        System.out.println("put key: " + key + " value: " + value);
        if (StringUtils.isValid(key)) {
            System.out.println("put");
            jedis.set(key, value);
        } else {
            throw new RedisException("Key null!");
        }
    }
    
    public static String get(String key) throws RedisException{
        if (StringUtils.isValid(key)) {
            return jedis.get(key);
        } else {
            throw new RedisException("Key null!");
        }
    }
    
    public static void putSet(String key, Set<String> value) {
        if (StringUtils.isValid(key)) {
            if (Validator.isValidCollection(value)) {
                for (String object : value) {
                    jedis.sadd(key, object);
                }
            } else {
                throw new RedisException("Value is valid! Value was either null or empty.");
            }
        } else {
            throw new RedisException("Key null!");
        }
    }
    
    public static void putToSet(String key, String... values) {
        jedis.sadd(key, values);
    }
    
    public static void putList(String key, List<String> value) {
        if (StringUtils.isValid(key)) {
            if (Validator.isValidCollection(value)) {
                for (String object : value) {
                    jedis.lpush(key, object);
                }
            } else {
                throw new RedisException("Value is valid! Value was either null or empty.");
            }
        } else {
            throw new RedisException("Key null!");
        }
    }
    
    static void putToList(String key, String... values) {
        jedis.lpush(key, values);
    }
    
    public static void putMap(String key, Map<String, String> values) {
        if (StringUtils.isValid(key)) {
            if (Validator.isValidMap(values)) {
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    String keyMap = entry.getKey();
                    String value = entry.getValue();
                    jedis.hset(key, keyMap, value);
                }
            }
        } else {
            throw new RedisException("Key null!");
        }
    }
}
