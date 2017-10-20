/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository;

import com.mycompany.usermanagementserver.server.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author tuantran
 */

public interface UserRepository extends MongoRepository<User, String>{
    
    public User findByEmail(String email);
}
