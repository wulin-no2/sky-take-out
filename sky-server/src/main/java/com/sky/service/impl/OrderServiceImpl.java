package com.sky.service.impl;


import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    /**
     * user submit order
     * @param ordersSubmitDTO
     * @return OrderSubmitVO
     */
    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {

        // get shopping cart
        Long currentId = BaseContext.getCurrentId();

        ShoppingCart shoppingCart = ShoppingCart
                .builder()
                .userId(currentId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        // get address
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());

        //check if address is null or shopping cart is null
        // if it is. throw an exception
        if (addressBook == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        if (list == null || list.isEmpty()){
            throw new OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        // copy DTO to orders object
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        // fill other fields
        orders.setStatus(Orders.UN_PAID);
        orders.setPayStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(currentId);
        orders.setOrderTime(LocalDateTime.now());
        // TODO: how to set Address??
        orders.setAddress(addressBook.getCityName()+addressBook.getDistrictName()
        +addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setUserName(shoppingCart.getName());
        orders.setConsignee(addressBook.getConsignee());

        orders.setNumber(String.valueOf(System.currentTimeMillis()));

        // insert into orders
        orderMapper.insert(orders);

        // copy shopping cart to orderDetail objects
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart item: list){
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(item,orderDetail);
            orderDetail.setOrderId(orders.getId());
            // insert into orderDetail
            orderDetails.add(orderDetail);
        }
        // insert into orderDetails
        orderDetailMapper.insert(orderDetails);

        // important!! clean shopping cart:
        shoppingCartMapper.deleteBatchByUserId(currentId);

        // create VO and return
        OrderSubmitVO orderSubmitVO = OrderSubmitVO
                .builder()
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime())
                .build();

        return orderSubmitVO;
    }
}
