package com.geekq.rabbitmqdatailwork.basic.deadmessage;

import com.geekq.rabbitmqdatailwork.basic.common.Constants;
import com.geekq.rabbitmqdatailwork.basic.util.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DeadProducer {

	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory = MqUtil.getConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = Constants.DLX_EXCHANGE_NAME;
		String routingKey = "dlx.save";
		
		String msg = "Hello RabbitMQ DLX Message";
		
		for(int i =0; i<1; i ++){
			
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")
					.build();

			/**
			 * 当mandatory标志位设置为true时，如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，
			 * 那么会调用basic.return方法将消息返回给生产者（Basic.Return + Content-Header + Content-Body）
			 * 当mandatory设置为false时，出现上述情形broker会直接将消息扔掉。
			 */
			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}
		
	}
}
