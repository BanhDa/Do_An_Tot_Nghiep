/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository;

import com.mycompany.usermanagementserver.server.domain.User;
import java.util.List;

/**
 *
 * @author tuantran
 */
public interface UserRepository{
    
    public User findByEmail(String email);
    
    public void save(User user);
    
    public User findByUserId(String userId);
    
    public List<User> searchByName(String userId, String searhUserName);
}
