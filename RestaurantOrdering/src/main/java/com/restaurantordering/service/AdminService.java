package com.restaurantordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurantordering.config.AppConsts;
import com.restaurantordering.dtos.*;
import com.restaurantordering.entity.*;
import com.restaurantordering.mapper.*;
import com.restaurantordering.utils.UserFriendlyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
@RequiredArgsConstructor
public class AdminService {

    final AdminMapper adminMapper;
    final UserMapper userMapper;
    final LoginInfoMapper loginInfoMapper;
    final OrdersMapper ordersMapper;
    final DishesMapper dishesMapper;
    final DishesTypeMapper dishesTypeMapper;

    public Admin login(String username, String password) {
        Admin admin = adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username)
                .eq(Admin::getPassword, password));
        UserFriendlyException.throwException(admin == null, "用户名或密码错误!");
        //存放登录信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginTime(new Date());
        loginInfo.setLoginUsername(admin.getUsername());
        loginInfo.setLoginName(admin.getName());
        loginInfo.setPhone(admin.getPhone());
        loginInfo.setRole(2);
        loginInfoMapper.insert(loginInfo);
        return admin;
    }

    public Page<Admin> getAdminList(Integer currentPage, Integer pageSize) {
        Page<Admin> userPage = new Page<>(currentPage, pageSize);
        return adminMapper.selectPage(userPage, null);
    }


    public void updateAdmin(Admin admin) {
        adminMapper.updateById(admin);
    }

    public boolean ruleStr(String str) {
        return str.length() < 4;
    }

    public Page<Admin> addAdmin(Admin admin) {
        //验证用户名密码
        UserFriendlyException.throwException(ruleStr(admin.getUsername()), "用户名长度不能小于4!");
        //设置初始密码
        admin.setPassword("123456");
        Admin e = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, admin.getUsername()));
        UserFriendlyException.throwException(e != null, "此用户名已被注册，请重新输入!");
        adminMapper.insert(admin);
        Page<Admin> userPage = new Page<>(1, 5);
        return adminMapper.selectPage(userPage, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<Admin> deleteAdmin(Integer adminId) {
        adminMapper.deleteById(adminId);
        Page<Admin> userPage = new Page<>(1, 5);
        return adminMapper.selectPage(userPage, null);
    }

    public List<Admin> searchAdmin(String name, String phone) {
        QueryWrapper<Admin> w = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            w.like("name", name);
        }
        if (StringUtils.isNotBlank(phone)) {
            w.eq("phone", phone);
        }
        return adminMapper.selectList(w);
    }

    public Page<Admin> resetPassword(Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        admin.setPassword("123456");
        adminMapper.updateById(admin);
        Page<Admin> userPage = new Page<>(1, 5);
        return adminMapper.selectPage(userPage, null);
    }

    public void updatePassword(String userType, Integer id, String password) {
        if (userType.equals("admin")) {
            Admin teacher = adminMapper.selectById(id);
            teacher.setPassword(password);
            adminMapper.updateById(teacher);
        }
        if (userType.equals("user")) {
            User user = userMapper.selectById(id);
            user.setPassword(password);
            userMapper.updateById(user);
        }
    }

    public UpdateInfoDto updateMyInfo(UpdateInfoDto dto) {
        Integer userType = dto.getUserType();
        if (userType == 1) {
            User user = userMapper.selectById(dto.getId());
            BeanUtils.copyProperties(dto, user);
            userMapper.updateById(user);
        }
        if (userType == 2) {
            Admin admin = adminMapper.selectById(dto.getId());
            BeanUtils.copyProperties(dto, admin);
            adminMapper.updateById(admin);
        }
        return dto;
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

    public String updateMyAvatar(MultipartFile file, Integer id, Integer userType) {
        String avatar = uploadAvatar(file);
        if (userType == 1) {
            User user = userMapper.selectById(id);
            user.setAvatar(avatar);
            userMapper.updateById(user);
        }
        if (userType == 2) {
            Admin admin = adminMapper.selectById(id);
            admin.setAvatar(avatar);
            adminMapper.updateById(admin);
        }
        return avatar;
    }

    public ChartsInfo getDataInfo() {
        ChartsInfo chartsInfo = new ChartsInfo();
        //系统总人数
        Integer admins = adminMapper.selectCount(null);
        Integer users = userMapper.selectCount(null);
        chartsInfo.setTotalPeople(admins + users);
        //订单数
        List<Orders> orders = ordersMapper.selectList(null);
        chartsInfo.setOrdersTotal(orders.size());
        //系统菜品数
        Integer dishesTotal = dishesMapper.selectCount(null);
        chartsInfo.setDishesTotal(dishesTotal);
        //系统菜品分类数
        BigDecimal totalPrice = new BigDecimal("0");
        for (Orders o : orders) {
            totalPrice = totalPrice.add(new BigDecimal(o.getTotalPrice()));
        }
        chartsInfo.setPriceTotal(totalPrice.toString());
        //各分类菜品数量
        List<DishesTypeChartsDto> list = dishesTypeMapper.selectDishesTypeCount();
        chartsInfo.setServeList(list);
        //获取近半个月的销售金额
        List<OrderPriceCharts> ops = ordersMapper.selectOrderPrice();
        chartsInfo.setList(ops);
        //获取近一个月活跃数
        List<LoginChartsDto> loginChartsDtos = loginInfoMapper.selectLoginInfo();
        chartsInfo.setLoginChartsList(loginChartsDtos);
        return chartsInfo;
    }
}
