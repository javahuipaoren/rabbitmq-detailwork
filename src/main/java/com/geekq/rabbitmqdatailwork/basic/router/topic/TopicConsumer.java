package com.geekq.rabbitmqdatailwork.basic.router.topic;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class TopicConsumer {

	public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = MqUtil.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
		//4 声明
		String exchangeName = Constants.TOPIC_EXCHANGE_NAME;
		String exchangeType = "topic";
		String queueName = "test_topic_queue";
		//String routingKey = "user.*";
		String routingKey = "user.*";
		// 1 声明交换机 
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		// 2 声明队列
		channel.queueDeclare(queueName, false, false, false, null);
		// 3 建立交换机和队列的绑定关系:
		channel.queueBind(queueName, exchangeName, routingKey);
		
        //durable 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //参数：队列名称、是否自动ACK、QosConsumer
        channel.basicConsume(queueName, true, consumer);  
        //循环获取消息  
        while(true){  
            //获取消息，如果没有消息，这一步将会一直阻塞  
            Delivery delivery = consumer.nextDelivery();  
            String msg = new String(delivery.getBody());    
            System.out.println("收到消息：" + msg);  
        } 
	}
}
