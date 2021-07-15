package com.geekq.rabbitmqdatailwork.basic.returnmessage;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author 邱润泽 bullock
 */
public class ReturnConsumer {

    public static void main(String[] args) throws Exception {


        ConnectionFactory factory = MqUtil.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = Constants.RETURN_EXCHANGE_NAME;
        String routingKey = "return.#";
        String queueName = "return_queue_0001";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName, true, queueingConsumer);

        while(true){

            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.err.println("消费者: " + msg);
        }





    }
}
