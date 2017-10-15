/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver.server.service.base;

import com.mycompany.usermannagementserver.server.domain.User;
import com.mycompany.usermannagementserver.exception.UserManagememtException;

/**
 *
 * @author tuantran
 */
public interface UserService extends ServiceBase{
    
    public User login(String email, String password) throws UserManagememtException;
    
    public User createUser(User user) throws UserManagememtException;
}
