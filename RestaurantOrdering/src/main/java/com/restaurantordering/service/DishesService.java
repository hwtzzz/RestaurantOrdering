package com.restaurantordering.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurantordering.entity.Dishes;
import com.restaurantordering.mapper.DishesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Service
public class DishesService {

    @Autowired
    DishesMapper dishesMapper;

    public Page<Dishes> getDishesList(Integer currentPage, Integer pageSize) {
        Page<Dishes> page = new Page<>(currentPage, pageSize);
        return dishesMapper.selectPage(page, null);
    }


    public void updateDishes(Dishes dishes) {
        dishesMapper.updateById(dishes);
    }

    public Page<Dishes> addDishes(Dishes dishes) {
        dishes.setCount(0);
        dishesMapper.insert(dishes);
        Page<Dishes> page = new Page<>(1, 5);
        return dishesMapper.selectPage(page, null);
    }
    @Transactional(rollbackFor = Exception.class)
    public Page<Dishes> deleteDishes(Integer DishesId) {
        dishesMapper.deleteById(DishesId);
        Page<Dishes> page = new Page<>(1, 5);
        return dishesMapper.selectPage(page, null);
    }
    public List<Dishes> searchDishes(String dishesName, Integer dishesType) {
        QueryWrapper<Dishes> w = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dishesName)) {
            w.like("dishesName", dishesName);
        }
        if (dishesType != null) {
            w.eq("dishesType", dishesType);
        }
        return dishesMapper.selectList(w);
    }

    public List<Dishes> getIndexDishes() {
        QueryWrapper<Dishes> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("count")
                .last("LIMIT 3");
        return dishesMapper.selectList(queryWrapper);
    }

    public List<Dishes> getAllDishes() {
        return dishesMapper.selectList(null);
    }
}
