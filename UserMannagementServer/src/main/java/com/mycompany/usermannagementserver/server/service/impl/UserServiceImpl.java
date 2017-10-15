/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver.server.service.impl;

import com.mycompany.usermannagementserver.server.domain.User;
import com.mycompany.usermannagementserver.server.repository.UserRepository;
import com.mycompany.usermannagementserver.server.service.base.UserService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.usermannagementserver.exception.UserManagememtException;
import com.mycompany.webchatutil.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tuantran
 */
@Service
public class UserServiceImpl implements UserService{

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
    
}
