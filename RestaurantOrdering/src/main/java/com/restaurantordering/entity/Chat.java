package com.restaurantordering.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantordering.config.AppConsts;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "chatId", type = IdType.AUTO)
    private Integer chatId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 消息
     */
    private String message;

    /**
     * 消息时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConsts.dateTimeFormatPattern, timezone = "GMT+8")
    @DateTimeFormat(pattern = AppConsts.dateTimeFormatPattern)
    private Date messageTime;

    /**
     * 聊天人角色
     */
    private Integer role;

    /**
     * 聊天人的
     */
    private Integer id;


}
