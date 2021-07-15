package com.geekq.rabbitmqdatailwork.basic.limitmessage;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class QosConsumer {

	
	public static void main(String[] args) throws Exception {


		ConnectionFactory connectionFactory = MqUtil.getConnectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		
		String exchangeName = Constants.QS_EXCHANGE_NAME;
		String queueName = "qos_queue";
		String routingKey = "qos.#";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

		/**
		 * 获取消息最大数[0-无限制], 依次获取数量, 作用域[true作用于整个channel，false作用于具体消费者]
		 *
		 *  限流方式  第一件事就是 autoAck设置为 false
		 *
		 *  进行消费，签收模式一定要为手动签收
		 */

		
		channel.basicQos(0, 1, false);
		
		channel.basicConsume(queueName, false, new MyConsumer(channel));
		
		
	}
}
