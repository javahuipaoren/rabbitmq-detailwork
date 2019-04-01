package com.geekq.rabbitmqreliable.service;

import com.geekq.rabbitmqreliable.entity.BrokerMessageLog;

import java.util.Map;

/**
 * @author 邱润泽 bullock
 */
public interface RabbitMqSendService {

    public void send(BrokerMessageLog messageLog);
}
