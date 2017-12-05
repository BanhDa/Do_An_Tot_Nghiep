/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.base;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.Image;

/**
 *
 * @author tuantran
 */
public interface FilesService {
    
    public Image saveImage(Image image) throws UserManagememtException;
    
    public Image updateAvatar(Image image) throws UserManagememtException;
    
    public Image getAvatarByUserId(String userId);
    
    public void writeFile(String path, byte[] data);
    
    public Image getImageByImageId(String imageId) throws UserManagememtException;
    
    public byte[] readFile(String urlFile) throws UserManagememtException;
    
    public String getAvatar(String userId);
}
