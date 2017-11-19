/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository.impl;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.common.ListUtils;
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
        
        ObjectId id = new ObjectId(userId);
        Query query = new Query(Criteria.where(UserDBKey.USER.USER_NAME).is(searhUserName)
                .andOperator(Criteria.where(UserDBKey.USER.ID).ne(id)));
        try {
            results = mongoOperations.find(query, User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return results;
    }
    
    private String createQuerySearchByUserName(String searchUserName) {
        String result = searchUserName;
        
        if (StringUtils.isValid(searchUserName)) {
            String[] parts = searchUserName.split(" ");
            result = "^";
            for (String part : parts) {
                int size = part.length();
                result = result + "[" + part + "]{0," + size +"}";
            }
            result = result + "$";
        }
        
        return result;
    }
}
