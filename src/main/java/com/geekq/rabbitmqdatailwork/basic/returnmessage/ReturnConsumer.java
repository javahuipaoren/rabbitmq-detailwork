package com.geekq.rabbitmqdatailwork.basic.returnmessage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author 邱润泽 bullock
 */
public class ReturnConsumer {

    public static void main(String[] args) throws Exception {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setPassword("mqadmin");
        factory.setUsername("mqadmin");
        factory.setHost("39.107.245.253");
        factory.setVirtualHost("/");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "return_00001";
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
