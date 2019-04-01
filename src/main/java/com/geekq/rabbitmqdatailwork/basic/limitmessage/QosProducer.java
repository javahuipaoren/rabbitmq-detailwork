package com.geekq.rabbitmqdatailwork.basic.limitmessage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class QosProducer {

	
	public static void main(String[] args) throws Exception {

		/**
		 *  建立连接通道
		 *  在非自动确认的模式下，可以采用限流模式，rabbitmq 提供了服务质量保障qos机制来控制一次消费消息数量。
		 */
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.245.253");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("mqadmin");
		connectionFactory.setPassword("mqadmin");


		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		String exchange = "qos_exchange_0001";
		String routingKey = "qos.save_0001";
		
		String msg = "Hello RabbitMQ QOS Message";
		
		for(int i =0; i<5; i ++){
			channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
			System.out.println("发送 ok !!");
		}

		/**
		 * 看看返回确认模式的类型
		 */
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("========= ack ===========");
			}

			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("========= nack ===========");

			}
		});
		
	}
}
