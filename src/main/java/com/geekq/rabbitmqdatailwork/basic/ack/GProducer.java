package com.geekq.rabbitmqdatailwork.basic.ack;

import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author 邱润泽 bullock
 */
public class GProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        /**
         *  建立连接通道
         */
        ConnectionFactory connectionFactory = MqUtil.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchange = "test_ack_exchange";
        String routingKey = "ack.go";

        for(int i =0; i<5; i ++){

            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("num", i);

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();
            String msg = "Hello RabbitMQ ACK Message " + i;
            channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
        }

    }
}
