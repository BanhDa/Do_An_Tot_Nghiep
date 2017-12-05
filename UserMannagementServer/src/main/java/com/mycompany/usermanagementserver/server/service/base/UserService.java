/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.base;

import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import java.util.List;

/**
 *
 * @author tuantran
 */
public interface UserService extends ServiceBase{
    
    public User login(String email, String password) throws UserManagememtException;
    
    public User createUser(User user) throws UserManagememtException;
    
    public User getUserInfo(String userId) throws UserManagememtException;
    
    public User updateUserInfo(User user) throws UserManagememtException;
    
    public List<User> searchUser(String userId, String searchUserName, Integer skip, Integer take);
    
    public List<User> getUsersInfo(List<String> userIds);
    
    public boolean updateAvatar(String userId, String avatarId);
}
