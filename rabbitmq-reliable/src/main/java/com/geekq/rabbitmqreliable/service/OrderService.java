package com.geekq.rabbitmqreliable.service;

import com.geekq.rabbitmqreliable.entity.Order;

/**
 * @author 邱润泽 bullock
 */
public interface OrderService {

    public void createOrder(String orderName);
}
