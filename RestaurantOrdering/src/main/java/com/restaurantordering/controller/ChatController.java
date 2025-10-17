package com.restaurantordering.controller;


import com.restaurantordering.dtos.MsgVO;
import com.restaurantordering.entity.Chat;
import com.restaurantordering.mapper.ChatMapper;
import com.restaurantordering.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    ChatMapper chatMapper;
    @Autowired
    ChatService chatService;

    /**
     * 获取聊天列表
     *
     * @return
     */
    @GetMapping("/getAllChat")
    public List<Chat> getAllChat() {
        return chatMapper.selectList(null);
    }

    /**
     * 添加聊天信息
     *
     * @param msg
     */
    @PostMapping("/addChat")
    public void addChat(@RequestBody MsgVO msg) {
        chatService.addChat(msg);
    }

}

