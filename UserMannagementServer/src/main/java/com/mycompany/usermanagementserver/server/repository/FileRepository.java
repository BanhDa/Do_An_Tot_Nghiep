/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.UserFile;

/**
 *
 * @author tuantran
 */
public interface FileRepository {
    
    public UserFile save(UserFile file) throws UserManagememtException;
    
    public UserFile findByFileId(String fileId) throws UserManagememtException;
}
