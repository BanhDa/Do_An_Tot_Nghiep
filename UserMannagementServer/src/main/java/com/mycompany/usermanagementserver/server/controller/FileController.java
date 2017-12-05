/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.common.Helper;
import com.mycompany.usermanagementserver.server.domain.Image;
import com.mycompany.usermanagementserver.server.request.Request;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.usermanagementserver.server.service.base.FilesService;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.usermanagementserver.server.service.base.UserService;
import com.mycompany.webchatutil.constant.Constant;
import com.mycompany.webchatutil.constant.FilesAndFolders;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tuantran
 */
@RestController
@RequestMapping("/file")
public class FileController {
    
    @Autowired
    private SessionService sessionService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FilesService filesService;
    @Autowired
    private UserService userService;
    
    @PostMapping(value="/uploadimage")
    public ResponseEntity<Response> singleFileUpload(@RequestParam("image") MultipartFile file, 
            @RequestParam("userid") String userId) {

        Response response = new Response();

        try {
//            if (!sessionService.checkToken(token)) {
//                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
//            }
            
            if (file != null && !file.isEmpty()) {
                
                byte[] bytes = file.getBytes();
                String fileName = Helper.createUrlPath(FilesAndFolders.IMAGE_JPG_EXTENSION);
                filesService.writeFile(fileName, bytes);
                
                Image image = new Image(userId, fileName, System.currentTimeMillis());
                filesService.saveImage(image);
                
                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(image);
            } else {
                throw  new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, ResponseMessage.IMAGE_ID_WRONG);
            }
        } catch (UserManagememtException e) {
            e.printStackTrace();
            response.setCode(e.getCode());
            response.setData(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value="/uploadavatar")
    public ResponseEntity<Response> uploadAvatar(@RequestParam("image") MultipartFile file, 
            @RequestParam("userid") String userId) {

        Response response = new Response();

        try {
            if (file != null && !file.isEmpty()) {
                
                byte[] bytes = file.getBytes();
                Image avatar = filesService.getAvatarByUserId(userId);
                String fileName;
                if (avatar != null && avatar.getPath() != null && !avatar.getPath().trim().isEmpty()) {
                    fileName = avatar.getPath();
                    avatar.setUploadTime(System.currentTimeMillis());
                    avatar.setIsAvatar(Constant.FLAG.ON);
                } else {
                    fileName = Helper.createUrlPath(FilesAndFolders.IMAGE_JPG_EXTENSION);
                    avatar = new Image(userId, fileName, System.currentTimeMillis(), Constant.FLAG.ON);
                }
                
                filesService.writeFile(fileName, bytes);
                filesService.saveImage(avatar);
                if ( userService.updateAvatar(userId, avatar.getImageId()) ) {
                    response.setCode(ResponseCode.SUCCESSFUL);
                    response.setData(avatar);
                } else {
                    throw new UserManagememtException(ResponseCode.SAVE_FILE_ERROR, ResponseMessage.SAVE_FILE_ERROR);
                }
            } else {
                throw  new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, ResponseMessage.IMAGE_ID_WRONG);
            }
        } catch (UserManagememtException e) {
            e.printStackTrace();
            response.setCode(e.getCode());
            response.setData(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/loadimage")
    public ResponseEntity<Response> loadImage(@RequestHeader(Request.AUTHORIZATION) String token,
                @RequestParam("imageid") String imageId) {
        Response response = new Response();
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
            }
            
            if (StringUtils.isValid(imageId)) {
                Image image = filesService.getImageByImageId(imageId);
                if (image != null) {
                    byte[] images = filesService.readFile(image.getPath());
                    String imageBase64 = Base64.getEncoder().encodeToString(images);
                    System.out.println(imageBase64);
                    
                    response.setCode(ResponseCode.SUCCESSFUL);
                    response.setData(imageBase64);
                } else {
                    throw new UserManagememtException(ResponseCode.FILE_NOT_FOUND, ResponseMessage.FILE_NOT_FOUND);
                }
            } else {
                throw new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, ResponseMessage.IMAGE_ID_WRONG);
            }
        } catch (UserManagememtException e) {
            e.printStackTrace();
            response.setCode(e.getCode());
            response.setData(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(images);
    }
    
    @PostMapping("/loadavatar")
    public ResponseEntity<Response> loadImage(@RequestHeader(Request.AUTHORIZATION) String token) {
        Response response = new Response();
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
            }
            
            String userId = tokenService.getUserId(token);
            Image avatar = filesService.getAvatarByUserId(userId);
            if (avatar != null) {
                byte[] images = filesService.readFile(avatar.getPath());
                String imageBase64 = Base64.getEncoder().encodeToString(images);
                System.out.println(imageBase64);

                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(imageBase64);
            } else {
                throw new UserManagememtException(ResponseCode.FILE_NOT_FOUND, ResponseMessage.FILE_NOT_FOUND);
            }
        } catch (UserManagememtException e) {
            e.printStackTrace();
            response.setCode(e.getCode());
            response.setData(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
