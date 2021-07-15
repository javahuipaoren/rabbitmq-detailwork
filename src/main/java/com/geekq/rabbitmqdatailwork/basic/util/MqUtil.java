package com.geekq.rabbitmqdatailwork.basic.util;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
import com.rabbitmq.client.ConnectionFactory;

public class MqUtil {

    public static ConnectionFactory getConnectionFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.HOST_NAME);
        connectionFactory.setPort(Constants.PORT);
        connectionFactory.setVirtualHost(Constants.VIRTUAL_NAME);
        connectionFactory.setUsername(Constants.USER_NAME);
        connectionFactory.setPassword(Constants.PASSWORD);
        return connectionFactory;
    }
}
