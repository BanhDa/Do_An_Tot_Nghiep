/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.base;

import com.mycompany.usermanagementserver.entity.message.Message;
import java.util.List;

/**
 *
 * @author tuantran
 */
public interface ChatService {
    
    public List<Message> getLastMessages(String userId);
}
