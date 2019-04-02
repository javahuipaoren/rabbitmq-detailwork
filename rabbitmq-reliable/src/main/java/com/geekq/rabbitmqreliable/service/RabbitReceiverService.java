package com.geekq.rabbitmqreliable.service;

import com.geekq.rabbitmqreliable.entity.BrokerMessageLog;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 邱润泽 bullock
 */
@Service
public class RabbitReceiverService {

    private static final Logger log = LoggerFactory.getLogger(RabbitReceiverService.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-order",
                    durable="true"),
            exchange = @Exchange(value = "order_exchange",
                    durable="true",
                    type= "topic"
                    ),
            key = "order.*"
    )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload BrokerMessageLog msg,
                               Channel channel,
                               @Headers Map<String, Object> headers) throws Exception {
        log.info("-----------------RabbitOrderReceiver---------------------");
        log.info("消费端 order处理逻辑 msg: {} ",  msg.toString());
        log.info("消费端 打印日志 headers: {} ",  headers.toString());
        Thread.sleep(2000L);
            Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            //手工ACK
            channel.basicAck(deliveryTag, false);
//        channel.basicNack(deliveryTag,false,true);
    }
}
