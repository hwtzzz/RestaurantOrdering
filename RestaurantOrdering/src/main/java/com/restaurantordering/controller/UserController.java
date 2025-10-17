package com.restaurantordering.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurantordering.config.wrapResult.DontWrapResult;
import com.restaurantordering.entity.User;
import com.restaurantordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login/{username}/{password}")
    public User login(@PathVariable String username, @PathVariable String password) {
        return userService.login(username, password);
    }

    /**
     * 获取所有用户 分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getUserList/{currentPage}/{pageSize}")
    public Page<User> getUserList(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        return userService.getUserList(currentPage, pageSize);
    }

    /**
     * 修改用户信息
     *
     * @param user
     */
    @PostMapping("/updateUser")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public Page<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/deleteUser/{userId}")
    public Page<User> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 查找用户
     *
     * @param user
     * @return
     */
    @PostMapping("/searchUser")
    public List<User> searchUser(@RequestBody User user) {
        return userService.searchUser(user.getName(), user.getPhone());
    }

    /**
     * 修改用户密码
     *
     * @param userId
     * @return
     */
    //前台使用
    @GetMapping("/resetPassword/{userId}")
    public Page<User> resetPassword(@PathVariable Integer userId) {
        return userService.resetPassword(userId);
    }

    @GetMapping("/getAllUserList")
    public List<User> getAllUser(){
        return userService.getgetAllUser();
    }

    /**
     * 修改个人信息
     * @param user
     */
    @PostMapping("/updateMyInfo")
    public void updateMyInfo(@RequestBody User user){
        userService.updateMyInfo(user);
    }

    /**
     * 用户修改头像
     * @param file
     * @param userId
     * @return
     */
    @DontWrapResult
    @PostMapping("/updateMyAvatar")
    public String updateMyAvatar(@RequestParam("file") MultipartFile file ,
                                 @RequestParam("userId") Integer userId) {
        return userService.updateMyAvatar(file , userId);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.register(user);
    }

}

