package com.geekq.rabbitmqdatailwork.basic.deadmessage;

import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class DeadConsumer {

	
	public static void main(String[] args) throws Exception {


		ConnectionFactory factory = MqUtil.getConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// 这就是一个普通的交换机 和 队列 以及路由
		String exchangeName = "dlx_exchange_0001";
		String routingKey = "dlx.#";
		String queueName = "test_dlx_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		
		Map<String, Object> agruments = new HashMap<String, Object>();
		agruments.put("x-dead-letter-exchange", "dlx.exchange");
		/**
		 * agruments 加入死信队列
		 *
		 * 有消息被拒绝（basic.reject/ basic.nack）并且requeue=false
		 * 2.队列达到最大长度
		 * 3.消息TTL过期
		 */
		channel.queueDeclare(queueName, true, false, false, agruments);
		channel.queueBind(queueName, exchangeName, routingKey);

		/**
		 * 死信队列声明
		 */
		channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
		channel.queueDeclare("dlx.queue", true, false, false, null);
		channel.queueBind("dlx.queue", "dlx.exchange", "#");
		
		channel.basicConsume(queueName, true, new MyConsumer(channel));
		
		
	}
}
