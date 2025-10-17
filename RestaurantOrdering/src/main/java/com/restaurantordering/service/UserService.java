package com.restaurantordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurantordering.config.AppConsts;
import com.restaurantordering.entity.LoginInfo;
import com.restaurantordering.entity.User;
import com.restaurantordering.mapper.LoginInfoMapper;
import com.restaurantordering.mapper.UserMapper;
import com.restaurantordering.utils.UserFriendlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    LoginInfoMapper loginInfoMapper;

    public User login(String username, String password) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password));
        UserFriendlyException.throwException(user == null, "用户名或密码错误!");
        //存放登录信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginTime(new Date());
        loginInfo.setLoginUsername(user.getUsername());
        loginInfo.setLoginName(user.getName());
        loginInfo.setPhone(user.getPhone());
        loginInfo.setRole(1);
        loginInfoMapper.insert(loginInfo);
        return user;
    }

    public Page<User> getUserList(Integer currentPage, Integer pageSize) {
        Page<User> userPage = new Page<>(currentPage, pageSize);
        return userMapper.selectPage(userPage, null);
    }


    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    public boolean ruleStr(String str) {
        return str.length() < 4;
    }

    public Page<User> addUser(User user) {
        //验证用户名密码
        UserFriendlyException.throwException(ruleStr(user.getUsername()) , "用户名长度不能小于4!");
        //设置初始密码
        user.setPassword("123456");
        User u = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        UserFriendlyException.throwException(u != null, "此用户名已被注册，请重新输入!");
        userMapper.insert(user);
        Page<User> userPage = new Page<>(1, 5);
        return userMapper.selectPage(userPage, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<User> deleteUser(Integer userId) {
        //todo删除对应的用户信息
        userMapper.deleteById(userId);
        Page<User> userPage = new Page<>(1, 5);
        return userMapper.selectPage(userPage, null);
    }

    public List<User> searchUser(String name, String phone) {
        QueryWrapper<User> w = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            w.like("name", name);
        }
        if (StringUtils.isNotBlank(phone)) {
            w.eq("phone", phone);
        }
        return userMapper.selectList(w);
    }
    public Page<User> resetPassword(Integer userId) {
        User user = userMapper.selectById(userId);
        user.setPassword("123456");
        userMapper.updateById(user);
        Page<User> userPage = new Page<>(1, 5);
        return userMapper.selectPage(userPage, null);
    }

    public List<User> getgetAllUser() {
        return userMapper.selectList(null);
    }

    public String uploadAvatar(MultipartFile file) {
        File fileDir = new File(AppConsts.IMAGE_PATH);
        if (!fileDir.exists()) {
            //如果没有目录应该创建目录
            fileDir.mkdirs();
        }
        //获取图片后缀
        String fileSuffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        //生成随机的文件名称 防止重复
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //生成文件保存位置名称
        String path = AppConsts.IMAGE_PATH + uuid + fileSuffix;
        //文件实现上传
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件访问地址
        return AppConsts.ACCESS_PATH + uuid + fileSuffix;
    }

    public String updateMyAvatar(MultipartFile file, Integer userId) {
        String avatar = uploadAvatar(file);
        User user = userMapper.selectById(userId);
        user.setAvatar(avatar);
        userMapper.updateById(user);
        return avatar;
    }

    public void updateMyInfo(User user) {
        userMapper.updateById(user);
    }

    public void register(User user) {
        //验证用户名密码
        UserFriendlyException.throwException(ruleStr(user.getUsername()), "用户名长度不能小于4!");
        User u = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        UserFriendlyException.throwException(u != null, "此用户名已被注册，请重新输入!");
        //设置初始密码
        user.setPassword("123456");
        //设置初始头像
        user.setAvatar("http://localhost:8888/image/user.jpg");
        userMapper.insert(user);
    }
}
