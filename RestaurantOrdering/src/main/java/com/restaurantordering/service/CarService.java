package com.restaurantordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.restaurantordering.entity.Car;
import com.restaurantordering.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CarService {

    @Autowired
    CarMapper carMapper;

    public int addCar(Integer userId, Integer dishesId) {
        //查看是否有相同的食物
        Car car = carMapper.selectOne(new LambdaQueryWrapper<Car>()
                .eq(Car::getUserId, userId)
                .eq(Car::getDishesId, dishesId));
        if (car != null) {
            car.setCount(car.getCount() + 1);
            carMapper.updateById(car);
        } else {
            Car c = new Car();
            c.setDishesId(dishesId);
            c.setCount(1);
            c.setUserId(userId);
            carMapper.insert(c);
        }
        return getMyCarCount(userId);
    }

    public int getMyCarCount(Integer userId) {
        return carMapper.selectCount(new LambdaQueryWrapper<Car>().eq(Car::getUserId, userId));
    }

    public List<Car> getMyCarList(Integer userId) {
        return carMapper.selectList(new LambdaQueryWrapper<Car>().eq(Car::getUserId , userId));
    }

    public List<Car> deleteCar(Integer carId) {
        Car car = carMapper.selectById(carId);
        carMapper.deleteById(carId);
        return getMyCarList(car.getUserId());
    }
}
