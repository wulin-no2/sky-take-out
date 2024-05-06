package com.sky.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
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
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private WebSocketServer webSocketServer;
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
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setPayStatus(Orders.UN_PAID);
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

        // create VO and return
        OrderSubmitVO orderSubmitVO = OrderSubmitVO
                .builder()
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime())
                .build();

        return orderSubmitVO;
    }

    /**
     * user pay the order
     * @param ordersPaymentDTO
     * @return EstimatedDeliveryTime
     */
    @Transactional
    public LocalDateTime payment(OrdersPaymentDTO ordersPaymentDTO) {
        // update orders:
        Orders order = Orders
                .builder()
                .status(Orders.TO_BE_CONFIRMED)
                .checkoutTime(LocalDateTime.now())
                .payMethod(ordersPaymentDTO.getPayMethod())
                .payStatus(Orders.PAID)
                .number(ordersPaymentDTO.getOrderNumber())
                .build();
        orderMapper.update(order);
        // get order estimatedDeliveryTime;
        Orders orders = orderMapper.getOrderByNumber(ordersPaymentDTO.getOrderNumber());

        // important!! clean shopping cart:
        // get userId
        Long currentId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteBatchByUserId(currentId);

        //send message to server once order successfully via websocket. type orderId content
        HashMap map = new HashMap();
        map.put("type",1);// 1 stands for new order; 2 stands for customer reminder
        map.put("orderId",orders.getId());
        map.put("content","order number: "+orders.getNumber());
        // convert to Json
        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);

        return orders.getEstimatedDeliveryTime();
    }

    /**
     * user reminder order
     * @param id
     */
    @Override
    public void reminder(Long id) {
        // get order by orderId
        Orders order = orderMapper.getById(id);

        // check if order exists
        if (order == null){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // use websocket to reminder
        HashMap map = new HashMap();
        map.put("type",2);
        map.put("orderId",id);
        map.put("content","order number:"+order.getNumber());

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);



    }

    /**
     * page orders
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        Page<Orders> orderPage = orderMapper.listOrders(ordersPageQueryDTO);
        long total = orderPage.getTotal();
        List<Orders> result = orderPage.getResult();
        return new PageResult(total, result);
    }
}
