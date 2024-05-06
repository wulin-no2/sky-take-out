package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);

    void update(Orders order);

    Orders getOrderByNumber(String number);

    List<Orders> listByStatusAndOrderTime(Integer status, LocalDateTime time);
@Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    Page<Orders> listOrders(OrdersPageQueryDTO ordersPageQueryDTO);
}
