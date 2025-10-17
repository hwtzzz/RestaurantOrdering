package com.restaurantordering.service;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.restaurantordering.dtos.IndexAppraiseDto;
import com.restaurantordering.dtos.OrdersListDto;
import com.restaurantordering.dtos.PageDto;
import com.restaurantordering.entity.*;
import com.restaurantordering.mapper.*;
import com.restaurantordering.utils.UserFriendlyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrdersItemMapper ordersItemMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    DishesMapper dishesMapper;

    public PageDto getOrdersList(Integer currentPage, Integer pageSize) {
        PageDto dto = new PageDto();
        Page<Orders> page = new Page<>(currentPage, pageSize);
        Page<Orders> ordersPage = ordersMapper.selectPage(page, null);
        List<Orders> records = ordersPage.getRecords();
        //拼接订单信息  麻婆豆腐*2、回锅肉*1
        ArrayList<OrdersListDto> ordersListDtos = new ArrayList<>();
        for (Orders record : records) {
            OrdersListDto ordersListDto = new OrdersListDto();
            BeanUtils.copyProperties(record, ordersListDto);
            User user = userMapper.selectById(record.getUserId());
            ordersListDto.setName(user.getName());
            ordersListDto.setPhone(user.getPhone());
            List<OrdersItem> ordersItems = ordersItemMapper.selectList(new LambdaQueryWrapper<OrdersItem>()
                    .eq(OrdersItem::getOrdersId, record.getOrdersId()));
            StringBuilder orderNames = new StringBuilder();
            for (int i1 = 0; i1 < ordersItems.size(); i1++) {
                OrdersItem ordersItem = ordersItems.get(i1);
                if (i1 > 0) { // 从第二个菜品开始添加分隔符
                    orderNames.append("、");
                }
                orderNames.append(ordersItem.getDishesName())
                        .append(" * ")
                        .append(ordersItem.getCount());
            }
            ordersListDto.setDishesNames(orderNames.toString());
            ordersListDtos.add(ordersListDto);
        }
        dto.setRecords(ordersListDtos);
        dto.setTotal((int) ordersPage.getTotal());
        return dto;
    }

    public PageDto updateOrderFlag(Integer ordersId, Integer flag) {
        Orders orders = ordersMapper.selectById(ordersId);
        orders.setFlag(flag);
        ordersMapper.updateById(orders);
        return getOrdersList(1, 8);
    }

    public List<OrdersListDto> searchOrder(Integer flag) {
        List<Orders> ordersList = ordersMapper.selectList(new LambdaQueryWrapper<Orders>()
                .eq(Orders::getFlag, flag));
        //拼接订单信息  麻婆豆腐*2、回锅肉*1
        ArrayList<OrdersListDto> ordersListDtos = new ArrayList<>();
        for (Orders record : ordersList) {
            OrdersListDto ordersListDto = new OrdersListDto();
            BeanUtils.copyProperties(record, ordersListDto);
            User user = userMapper.selectById(record.getUserId());
            ordersListDto.setName(user.getName());
            ordersListDto.setPhone(user.getPhone());
            List<OrdersItem> ordersItems = ordersItemMapper.selectList(new LambdaQueryWrapper<OrdersItem>()
                    .eq(OrdersItem::getOrdersId, record.getOrdersId()));
            StringBuilder orderNames = new StringBuilder();
            for (int i1 = 0; i1 < ordersItems.size(); i1++) {
                OrdersItem ordersItem = ordersItems.get(i1);
                if (i1 > 0) { // 从第二个菜品开始添加分隔符
                    orderNames.append("、");
                }
                orderNames.append(ordersItem.getDishesName())
                        .append(" * ")
                        .append(ordersItem.getCount());
            }
            ordersListDto.setDishesNames(orderNames.toString());
            ordersListDtos.add(ordersListDto);
        }
        return ordersListDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Dishes> addOrder(Integer userId) {
        List<Car> cars = carMapper.selectList(new LambdaQueryWrapper<Car>().eq(Car::getUserId, userId));
        ArrayList<OrdersItem> ordersItems = new ArrayList<>();
        BigDecimal bigDecimal = new BigDecimal("0");
        for (Car car : cars) {
            //加入到订单详情列表
            Integer dishesId = car.getDishesId();
            Dishes dishes = dishesMapper.selectById(dishesId);
            OrdersItem ordersItem = new OrdersItem();
            ordersItem.setCount(car.getCount());
            //计算订单详情金额并累加总金额
            BigDecimal multiply = BigDecimal.valueOf(dishes.getPrice()).multiply(new BigDecimal(car.getCount()));
            ordersItem.setTotal(multiply);
            bigDecimal = bigDecimal.add(multiply);
            ordersItem.setDishesName(dishes.getDishesName());
            ordersItems.add(ordersItem);
            //修改美食的库存和购买人数
            Dishes d = dishesMapper.selectById(car.getDishesId());
            UserFriendlyException.throwException(d.getDishesCount() < car.getCount(), dishes.getDishesName() + "库存不足!");
            //更新购买人数
            dishes.setCount(dishes.getCount() + 1);
            dishes.setDishesCount(dishes.getDishesCount() - car.getCount());
            dishesMapper.updateById(d);
        }
        //添加总订单信息
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setTotalPrice(bigDecimal.toString());
        orders.setOrderTime(new Date());
        orders.setFlag(1);
        ordersMapper.insert(orders);
        //有了总订单id才能插入订单详情
        for (OrdersItem ordersItem : ordersItems) {
            ordersItem.setOrdersId(orders.getOrdersId());
            ordersItemMapper.insert(ordersItem);
        }
        //删除购物车信息
        carMapper.delete(new LambdaQueryWrapper<Car>().eq(Car::getUserId, userId));
        return dishesMapper.selectList(null);
    }

    public List<OrdersListDto> getMyOrders(Integer userId) {
        List<Orders> ordersList = ordersMapper.selectList(new LambdaQueryWrapper<Orders>()
                .eq(Orders::getUserId, userId));
        //拼接订单信息
        ArrayList<OrdersListDto> ordersListDtos = new ArrayList<>();
        for (Orders record : ordersList) {
            OrdersListDto ordersListDto = new OrdersListDto();
            BeanUtils.copyProperties(record, ordersListDto);
            User user = userMapper.selectById(record.getUserId());
            ordersListDto.setName(user.getName());
            ordersListDto.setPhone(user.getPhone());
            List<OrdersItem> ordersItems = ordersItemMapper.selectList(new LambdaQueryWrapper<OrdersItem>()
                    .eq(OrdersItem::getOrdersId, record.getOrdersId()));
            StringBuilder orderNames = new StringBuilder();
            for (int i1 = 0; i1 < ordersItems.size(); i1++) {
                OrdersItem ordersItem = ordersItems.get(i1);
                if (i1 > 0) { // 从第二个菜品开始添加分隔符
                    orderNames.append("、");
                }
                orderNames.append(ordersItem.getDishesName())
                        .append(" * ")
                        .append(ordersItem.getCount());
            }
            ordersListDto.setDishesNames(orderNames.toString());
            ordersListDtos.add(ordersListDto);
        }
        return ordersListDtos;
    }

    public List<OrdersListDto> addAppraise(Integer ordersId, Integer star, String appraise) {
        Orders orders = ordersMapper.selectById(ordersId);
        orders.setFlag(4);
        orders.setStar(star);
        orders.setAppraise(appraise);
        ordersMapper.updateById(orders);
        return getMyOrders(orders.getUserId());
    }

    public List<IndexAppraiseDto> getIndexAppraise() {
        return ordersMapper.getIndexAppraise();
    }
}
