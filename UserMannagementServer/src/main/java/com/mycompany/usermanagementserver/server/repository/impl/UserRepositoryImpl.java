/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository.impl;

import com.mongodb.MongoClient;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.repository.UserRepository;
import com.mycompany.webchatutil.constant.Constant;
import com.mycompany.webchatutil.constant.mongodbkey.UserDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import com.mycompany.webchatutil.utils.Validator;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuantran
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private MongoOperations mongoOperations;
    
    @Autowired
    public UserRepositoryImpl(MongoClient mongoClient) {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, UserDBKey.USER_DB_NAME);
        mongoOperations = new MongoTemplate(factory);
    }
    
    @Override
    public void save(User user) throws UserManagememtException{
        mongoOperations.save(user);
    }
    
    @Override
    public User findByEmail(String email) throws UserManagememtException{
        Query query = new Query(Criteria.where(UserDBKey.USER.EMAIL).is(email));
        User searchUser = null;
        try {
            searchUser = mongoOperations.findOne(query, User.class);
        } catch (Exception ex) {
            System.out.println("search error!");
            ex.printStackTrace();
        }
        return searchUser;
    }
    
    @Override
    public boolean checkExistEmail(String userId, String email) {
        Query query = new Query(Criteria.where(UserDBKey.USER.EMAIL).is(email));
        ObjectId id = new ObjectId(userId);
        query.addCriteria(Criteria.where(UserDBKey.USER.ID).ne(id));
        
        try {
            User searchUser = mongoOperations.findOne(query, User.class);
            return searchUser != null;
        } catch (Exception ex) {
            System.out.println("search error!");
            ex.printStackTrace();
        }
        return true;
    }
    
    @Override
    public User findByUserId(String userId) {
        Query query = new Query(Criteria.where(UserDBKey.USER.ID).is(new ObjectId(userId)));
        User resultUser = null;
        
        try {
            resultUser = mongoOperations.findOne(query, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultUser;
    }
    
    @Override
    public List<User> getUsersInfo(List<String> userIds) {
        List<User> results = new ArrayList<>();
        if (userIds != null && !userIds.isEmpty()) {
            List<ObjectId> ids = new ArrayList<>();
            
            userIds.stream().map((userId) -> new ObjectId(userId)).forEachOrdered((id) -> {
                ids.add(id);
            });
            
            Query query = new Query(Criteria.where(UserDBKey.USER.ID).in(ids));
            results = mongoOperations.find(query, User.class);
        }
            
        return results;
    }
    
    @Override
    public List<User> searchByName(String userId, String searhUserName) {
        List<User> results = new ArrayList<>();
        
        ObjectId id = new ObjectId(userId);
        Query query = new Query(Criteria.where(UserDBKey.USER.ID).ne(id));
        if (Validator.isEmail(searhUserName)) {
            query.addCriteria(Criteria.where(UserDBKey.USER.EMAIL).is(searhUserName));
        } else {
            String regex = createQuerySearchByUserName(searhUserName);
            query.addCriteria(Criteria.where(UserDBKey.USER.USER_NAME).regex(regex));
        }
        try {
            results = mongoOperations.find(query, User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return results;
    }
    
    @Override
    public boolean updateAvatar(String userId, String avatarId) {
        if (StringUtils.isValid(userId, avatarId)) {
            ObjectId id = new ObjectId(userId);
            Query query = new Query(Criteria.where(UserDBKey.USER.ID).is(id));
            User user = mongoOperations.findOne(query, User.class);
            if (user != null) {
                user.setAvatar(avatarId);
                mongoOperations.save(user);
                return true;
            }
        }
        return false;
    }
    
    private String createQuerySearchByUserName(String searchUserName) {
        String result = null;
        
        if (StringUtils.isValid(searchUserName)) {
            for (String str : Constant.SPECIAL_CHARACTER) {
                if (searchUserName.contains(str)) {
                    String string = "\\" + str;
                    searchUserName = searchUserName.replace(str, string);
                }
                
                String[] list = searchUserName.trim().split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.length; i++) {
                    sb.append(list[i].toLowerCase());
                    if (i < (list.length - 1)) {
                        sb.append("|");
                    }
                }
                result = sb.toString();
            }
        }
        return result;
    }

}
