package com.restaurantordering.controller;

import com.alibaba.fastjson.JSONObject;
import com.restaurantordering.dtos.MsgVO;
import com.restaurantordering.entity.Admin;
import com.restaurantordering.entity.User;
import com.restaurantordering.mapper.AdminMapper;
import com.restaurantordering.mapper.UserMapper;
import com.restaurantordering.utils.ApplicationContextProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@ServerEndpoint("/groupChat/{sid}/{id}/{role}")
public class WebSocketServerController {

    /**
     * 房间号 -> 组成员信息
     */
    private static ConcurrentHashMap<String, CopyOnWriteArrayList<Session>> groupMemberInfoMap = new ConcurrentHashMap<>();

    /**
     * 房间号 -> 在线用户唯一标识集合（role_id）
     */
    private static ConcurrentHashMap<String, Set<String>> onlineUserMap = new ConcurrentHashMap<>();

    /**
     * 收到消息调用的方法，群成员发送消息
     */
    @OnMessage
    public void onMessage(@PathParam("sid") String sid, @PathParam("id") Integer id, String message) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        Set<String> onlineUserList = onlineUserMap.get(sid);
        sessionList.forEach(item -> {
            try {
                MsgVO msg = JSONObject.parseObject(message, MsgVO.class);
                msg.setCount(onlineUserList.size());  // 使用组合ID后的正确人数
                item.getBasicRemote().sendText(JSONObject.toJSONString(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 建立连接调用的方法，群成员加入
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("sid") String sid,
                       @PathParam("id") Integer id,
                       @PathParam("role") Integer role) {

        // 生成唯一用户标识
        String userKey = role + "_" + id;

        // 初始化数据结构
        groupMemberInfoMap.computeIfAbsent(sid, k -> new CopyOnWriteArrayList<>()).add(session);
        onlineUserMap.computeIfAbsent(sid, k -> ConcurrentHashMap.newKeySet()).add(userKey);

        // 发送上线通知
        sendInfo(sid, id, onlineUserMap.get(sid).size(), role, "上线了~");
    }

    /**
     * 关闭连接调用的方法，群成员退出
     */
    @OnClose
    public void onClose(Session session,
                        @PathParam("sid") String sid,
                        @PathParam("id") Integer id,
                        @PathParam("role") Integer role) {

        // 生成唯一用户标识
        String userKey = role + "_" + id;

        // 移除会话和用户标识
        groupMemberInfoMap.get(sid).remove(session);
        onlineUserMap.get(sid).remove(userKey);

        // 发送离线通知
        sendInfo(sid, id, onlineUserMap.get(sid).size(), role, "下线了~");

        // 清理空房间
        if (groupMemberInfoMap.get(sid).isEmpty()) {
            groupMemberInfoMap.remove(sid);
            onlineUserMap.remove(sid);
        }
    }

    /**
     * 通用消息发送方法
     */
    private void sendInfo(String sid, Integer id, Integer onlineSum, Integer role, String info) {
        MsgVO msg = new MsgVO();
        String userName = "";
        String avatar = "";

        // 根据角色类型获取用户信息
        try {
            switch (role) {
                case 1:
                    User user = ApplicationContextProvider.getBean(UserMapper.class).selectById(id);
                    userName = user.getName();
                    avatar = user.getAvatar();
                    break;
                case 2:
                    Admin admin = ApplicationContextProvider.getBean(AdminMapper.class).selectById(id);
                    userName = admin.getName();
                    avatar = admin.getAvatar();
                    break;
            }
        } catch (Exception e) {
            log.error("用户信息获取失败", e);
        }

        // 构建消息对象
        msg.setId(id);
        msg.setRole(role);
        msg.setAvatar(avatar);
        msg.setMessage(userName + info);
        msg.setCount(onlineSum);

        // 广播消息
        onMessage(sid, id, JSONObject.toJSONString(msg));
    }

    @OnError
    public void onError(Throwable error) {
        log.error("WebSocket连接错误", error);
    }
}