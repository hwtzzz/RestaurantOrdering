package com.restaurantordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.restaurantordering.entity.Dishes;
import com.restaurantordering.entity.DishesType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurantordering.mapper.DishesMapper;
import com.restaurantordering.mapper.DishesTypeMapper;
import com.restaurantordering.utils.UserFriendlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2025-03-06
 */
@Service
public class DishesTypeService {

    @Autowired
    DishesTypeMapper dishesTypeMapper;
    @Autowired
    DishesMapper dishesMapper;

    public List<DishesType> getDishesTypeList() {
        return dishesTypeMapper.selectList(null);
    }

    public List<DishesType> updateDishesType(DishesType dishesType) {
        dishesTypeMapper.updateById(dishesType);
        return getDishesTypeList();
    }

    public List<DishesType> addDishesType(DishesType dishesType) {
        dishesTypeMapper.insert(dishesType);
        return getDishesTypeList();
    }

    public List<DishesType> deleteDishesType(Integer dishesTypeId) {
        //删除之前判断这个分类下是否有菜品
        Integer count = dishesMapper.selectCount(new LambdaQueryWrapper<Dishes>()
                .eq(Dishes::getDishesType, dishesTypeId));
        UserFriendlyException.throwException(count > 0 , "请先删除此分类下的商品信息!");
        dishesTypeMapper.deleteById(dishesTypeId);
        return getDishesTypeList();
    }

    public List<DishesType> searchDishesType(String dishesTypeName) {
        return dishesTypeMapper.selectList(new LambdaQueryWrapper<DishesType>()
                .like(DishesType::getDishesTypeName , dishesTypeName));
    }
}
