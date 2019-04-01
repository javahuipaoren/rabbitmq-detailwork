package com.geekq.rabbitmqreliable.service.impl;

import com.alibaba.fastjson.JSON;
import com.geekq.rabbitmqreliable.constants.Constants;
import com.geekq.rabbitmqreliable.entity.BrokerMessageLog;
import com.geekq.rabbitmqreliable.entity.Order;
import com.geekq.rabbitmqreliable.mapper.BrokerMessageLogMapper;
import com.geekq.rabbitmqreliable.mapper.OrderMapper;
import com.geekq.rabbitmqreliable.service.OrderService;
import com.geekq.rabbitmqreliable.service.RabbitMqSendService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static com.geekq.rabbitmqreliable.constants.Constants.ORDER_SENDING;
import static com.geekq.rabbitmqreliable.constants.Constants.ORDER_TIMEOUT;

/**
 * @author 邱润泽 bullock
 */
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper ;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private RabbitMqSendService rabbitMqSendService ;

    @Override
    public void createOrder(Order order) {

        //TODO
        // order current time
        Date orderTime = new Date();
        // order insert
        orderMapper.insert(order);
        // log insert
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(order.getMessageId());
        //save order message as json
        brokerMessageLog.setMessage(JSON.toJSONString(order));
        brokerMessageLog.setStatus(ORDER_SENDING);
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime,ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insert(brokerMessageLog);
        // order message sender
//        rabbitMqSendService.sendOrder(order);
//        rabbitMqSendService.send();
    }
}
