package com.geekq.rabbitmqdatailwork.basic.router.direct;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 邱润泽 bullock
 *
 *  ack机制 是为了确保消息的数据能够被正确处理 告知用户消费者的状态
 */
public class DirectConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = MqUtil.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = Constants.DIRECT_EXCHANGE_NAME;
        String queueName = "direct_0";
        String routingKey = "direct.p";

        channel.exchangeDeclare(exchangeName, "direct", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        /**
         *  队列名称
         *  是否为自动应答
         *  消费者对象接口
         */
        channel.basicConsume(queueName, false, new DirectMConsumer(channel));


    }
}
