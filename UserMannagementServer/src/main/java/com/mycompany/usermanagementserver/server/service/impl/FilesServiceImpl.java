/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.impl;

import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.Image;
import com.mycompany.usermanagementserver.server.domain.UserFile;
import com.mycompany.usermanagementserver.server.repository.FileRepository;
import com.mycompany.usermanagementserver.server.repository.ImageRepository;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.usermanagementserver.server.service.base.FilesService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tuantran
 */
@Service
public class FilesServiceImpl implements FilesService{
    
    private static final Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private Config config;
    
    @Override
    public Image saveImage(Image image) throws UserManagememtException {
        if (image != null) {
            return imageRepository.save(image);
        }
        throw new UserManagememtException(ResponseCode.SAVE_FILE_ERROR, ResponseMessage.SAVE_FILE_ERROR);
    }
    
    @Override
    public void writeImageFile(String fileName, byte[] data) {
        if (StringUtils.isValid(fileName) 
                && data != null
                && data.length > 0) {
            
            try {
                String urlImage = config.folderImage + fileName;
                Path path = Paths.get(urlImage);
                Files.write(path, data);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new UserManagememtException(ResponseCode.WRITE_FILE_ERROR, ResponseMessage.WRITE_FILE_ERROR);
            }
        }
    }
    
    @Override
    public void writeFile(String fileName, byte[] data) {
        if (StringUtils.isValid(fileName) 
                && data != null
                && data.length > 0) {
            
            try {
                String urlImage = config.folderFile + fileName;
                Path path = Paths.get(urlImage);
                Files.write(path, data);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new UserManagememtException(ResponseCode.WRITE_FILE_ERROR, ResponseMessage.WRITE_FILE_ERROR);
            }
        }
    }
    
    @Override
    public Image getImageByImageId(String imageId) throws UserManagememtException {
        if (StringUtils.isValid(imageId)) {
            return imageRepository.findByImageId(imageId);
        } else {
            throw new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, ResponseMessage.IMAGE_ID_WRONG);
        }
    }
    
    @Override
    public byte[] readFile(String fileName) throws UserManagememtException {
        
        try {
            String url = config.folderFile + fileName;
            Path path = Paths.get(url);
            return Files.readAllBytes(path);
        } catch (IOException ex ) {
            ex.printStackTrace();
        }
        throw new UserManagememtException(ResponseCode.FILE_NOT_FOUND, ResponseMessage.FILE_NOT_FOUND);
    }
    
    @Override
    public byte[] readImage(String fileName) throws UserManagememtException {
        
        try {
            String url = config.folderImage + fileName;
            Path path = Paths.get(url);
            return Files.readAllBytes(path);
        } catch (IOException ex ) {
            ex.printStackTrace();
        }
        throw new UserManagememtException(ResponseCode.FILE_NOT_FOUND, ResponseMessage.FILE_NOT_FOUND);
    }

    @Override
    public Image updateAvatar(Image image) throws UserManagememtException {
        return imageRepository.save(image);
    }

    @Override
    public Image getAvatarByUserId(String userId) {
        return imageRepository.findAvatar(userId);
    }

    @Override
    public String getAvatar(String userId) {
        String result = null;
        if (StringUtils.isValid(userId)) {
            Image image = imageRepository.findAvatar(userId);
            if (image != null) {
                result = image.getImageId();
            }
        }
        
        return result;
    }
    
    @Override
    public String getAvatarResourceByAvatarId(String avatarId) {
        String result = null;
        
        if (StringUtils.isValid(avatarId)) {
            Image image = getImageByImageId(avatarId);
            if (image != null) {
                byte[] src = readImage( image.getPath() );
                result = Base64.getEncoder().encodeToString(src);
            }
        }
        
        return result;
    }
    
    @Override
    public String getFileExtension(String orginalFileName) {
        String result = null;
        
        if (StringUtils.isValid(orginalFileName)) {
            String[] parts = orginalFileName.split("\\.");
            result = parts[parts.length - 1];
        }
        
        return result;
    }
    
    @Override
    public UserFile saveFile(UserFile file) throws UserManagememtException {
        if (file != null) {
            return fileRepository.save(file);
        }
        throw new UserManagememtException(ResponseCode.SAVE_FILE_ERROR, ResponseMessage.SAVE_FILE_ERROR);
    }
    
    @Override
    public UserFile getFileByFileId(String fileId) throws UserManagememtException {
        if (StringUtils.isValid(fileId)) {
            return fileRepository.findByFileId(fileId);
        } else {
            throw new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, ResponseMessage.IMAGE_ID_WRONG);
        }
    }
}
