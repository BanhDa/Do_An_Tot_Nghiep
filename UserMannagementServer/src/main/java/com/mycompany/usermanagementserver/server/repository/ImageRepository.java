/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.Image;

/**
 *
 * @author tuantran
 */
public interface ImageRepository {
    
    public Image save(Image image) throws UserManagememtException;
    
    public Image findByImageId(String imageId) throws UserManagememtException;
}
