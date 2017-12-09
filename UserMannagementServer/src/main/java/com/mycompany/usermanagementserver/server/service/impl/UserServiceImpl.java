/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.impl;

import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.repository.UserRepository;
import com.mycompany.usermanagementserver.server.service.base.UserService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.common.ListUtils;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.webchatutil.utils.StringUtils;
import com.mycompany.webchatutil.utils.Validator;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tuantran
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User login(String email, String password)  throws UserManagememtException {
        User searchedUser = userRepository.findByEmail(email);
        
        if (!Validator.validateObject(searchedUser)) {
            throw new UserManagememtException(ResponseCode.EMAIL_NOT_FOUND, "Email not found!");
        }
        
        if (!searchedUser.getPassword().equals(password)) {
            throw new UserManagememtException(ResponseCode.INVALID_PASSWORD, "Invalid password!");
        }
        
        return searchedUser;
    }
    
    @Override
    public User createUser(User user) throws UserManagememtException{
        User searchedUser = userRepository.findByEmail(user.getEmail());
        if (Validator.validateObject(searchedUser)) {
            throw new UserManagememtException(ResponseCode.EXISTED_EMAIL, "The email was registered!");
        }
        userRepository.save(user);
        return user;
    }
    
    @Override
    public User getUserInfo(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UserManagememtException(ResponseCode.NOT_EXIST_USER, "NOT_EXIST_USER");
        }
        return user;
    }
    
    @Override
    public User updateUserInfo(User user) throws UserManagememtException{
        if (userRepository.checkExistEmail(user.getUserId(), user.getEmail())) {
            throw new UserManagememtException(ResponseCode.EXISTED_EMAIL, ResponseMessage.EXISTED_EMAIL);
        }
        
        User searchUser = userRepository.findByUserId(user.getUserId());
        if (searchUser == null) {
            throw new UserManagememtException(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        }
        searchUser = createUpdateUser(user, searchUser);
        userRepository.save(searchUser);
        return searchUser;
    }
    
    @Override
    public List<User> searchUser(String userId, String searchUserName, Integer skip, Integer take) {
        List<User> results = new ArrayList<>();
        
        try {
            results = userRepository.searchByName(userId, searchUserName);
            ListUtils<User> listUtils = new ListUtils<>();
            results = listUtils.sublist(results, skip, take);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return results;
    }
    
    @Override
    public List<User> getUsersInfo(List<String> userIds) {
        List<User> results = new ArrayList<>();
        
        if (userIds != null && !userIds.isEmpty()) {
            results = userRepository.getUsersInfo(userIds);
        }
        
        return results;
    }
    
    @Override
    public boolean updateAvatar(String userId, String avatarId) {
        return userRepository.updateAvatar(userId, avatarId);
    }
    
    private User createUpdateUser(User updateUser, User searchUser) {
        if ( StringUtils.isValid( updateUser.getAvatar() ) ) {
            searchUser.setAvatar( updateUser.getAvatar() );
        }
        if ( StringUtils.isValid( updateUser.getBirthday() ) ) {
            searchUser.setBirthday( updateUser.getBirthday() );
        }
        if ( StringUtils.isValid( updateUser.getEmail() ) ) {
            searchUser.setEmail( updateUser.getEmail() );
        }
        if (updateUser.getGender() != null) {
            searchUser.setGender( updateUser.getGender() );
        }
        if ( StringUtils.isValid( updateUser.getPassword() ) ) {
            searchUser.setPassword( updateUser.getPassword() );
        }
        if ( StringUtils.isValid( updateUser.getUserName() ) ) {
            searchUser.setUserName( updateUser.getUserName() );
        }
        return searchUser;
    }

}
