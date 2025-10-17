package com.restaurantordering.service;

import com.restaurantordering.dtos.MsgVO;
import com.restaurantordering.entity.Chat;
import com.restaurantordering.mapper.ChatMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Service
public class ChatService {

    @Autowired
    ChatMapper chatMapper;


    public void addChat(MsgVO msg) {
        Chat chat = new Chat();
        BeanUtils.copyProperties(msg , chat);
        chat.setMessageTime(new Date());
        chatMapper.insert(chat);
    }

}
