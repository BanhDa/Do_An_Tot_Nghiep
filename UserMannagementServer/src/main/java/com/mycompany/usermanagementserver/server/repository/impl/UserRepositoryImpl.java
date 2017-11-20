/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository.impl;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.common.Helper;
import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.repository.UserRepository;
import com.mycompany.webchatutil.constant.UserDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuantran
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    
    @Autowired
    private MongoOperations mongoOperations;
    
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
    public List<User> searchByName(String userId, String searhUserName) {
        List<User> results = new ArrayList<>();
        
        Query query = createQuerySearchUser(userId, searhUserName);
        try {
            results = mongoOperations.find(query, User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return results;
    }
    
    private Query createQuerySearchUser(String userId, String searchUserName) {
        Query query = new Query();
        
        if (StringUtils.isValid(userId, searchUserName)) {
            query.addCriteria(Criteria.where(UserDBKey.USER.ID).ne( new ObjectId(userId)));
            
            //search by email
            if (Helper.isEmail(searchUserName)) {
                query.addCriteria(Criteria.where(UserDBKey.USER.EMAIL).is(searchUserName));
            } else {  // seach by name
                String[] parts = searchUserName.split("\\s+");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < parts.length; i++) {
                    builder.append(parts[i]);
                    if (i < parts.length - 1) {
                        builder.append("|");
                    }
                }
                
                query.addCriteria(Criteria.where(UserDBKey.USER.USER_NAME).regex( builder.toString() ));
            }
            
        }
        
        return query;
    }
}
