package com.geekq.rabbitmqdatailwork.basic.returnmessage;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 邱润泽 bullock
 */
public class ReturnProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPassword("mqadmin");
        factory.setUsername("mqadmin");
        factory.setHost("39.107.245.253");
        factory.setVirtualHost("/");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName   = "return_00001" ;
        String rkey = "return.save";
        String routingKeyError = "abc.save";

        String msg = " Hello RabbitMQ Return Message " ;

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.err.println("---------handle  return----------");
                System.err.println("replyCode: " + replyCode);
                System.err.println("replyText: " + replyText);
                System.err.println("exchange: " + exchange);
                System.err.println("routingKey: " + routingKey);
                System.err.println("properties: " + properties);
                System.err.println("body: " + new String(body));
            }
        });

        channel.basicPublish(exchangeName,routingKeyError,true,null,msg.getBytes());

    }


}
