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
@TableName("login_info")
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "loginId", type = IdType.AUTO)
    private Integer loginId;

    /**
     * 登录时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConsts.dateTimeFormatPattern, timezone = "GMT+8")
    @DateTimeFormat(pattern = AppConsts.dateTimeFormatPattern)
    private Date loginTime;

    /**
     * 登录账号
     */
    private String loginUsername;

    /**
     * 登录人真实姓名
     */
    private String loginName;

    /**
     * 登录人电话
     */
    private String phone;

    /**
     * 1学生 2管理员 3老师 4专家
     */
    private Integer role;


}
