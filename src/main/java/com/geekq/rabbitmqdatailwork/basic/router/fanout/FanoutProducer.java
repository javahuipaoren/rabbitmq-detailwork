package com.geekq.rabbitmqdatailwork.basic.router.fanout;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
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
public class FanoutProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = MqUtil.getConnectionFactory();
        // 设置自动连接恢复
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        // 重试时间
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = Constants.FANOUT_EXCHANGE_NAME;

        for (int i = 0; i < 5 ; i++) {
            Map<String,Object> header = new HashMap<>();
            header.put("we","we are geek!");
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().deliveryMode(1)
                    .contentEncoding("UTF-8").headers(header).build();

            String msg = "we all geek to rabbitmq " +i ;
            channel.basicPublish(exchangeName,"",basicProperties,msg.getBytes());
        }

    }
}
