package com.geekq.rabbitmqdatailwork.basic.router.fanout;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author 邱润泽 bullock
 */
public class FanoutMConsumer extends DefaultConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FanoutMConsumer.class);

    private Channel channel ;
    /**
     * 构建一个新的实例
     *
     * @param channel the channel to which this consumer is attached
     */
    public FanoutMConsumer(Channel channel) {
        super(channel);
        this.channel = channel ;
    }

    /**
     * ------  消费者receive 信息 ------
     *  consumerTag :amq.ctag-_OGoymh3D9qPuT-y0ZdZ_g
     *  envelope :Envelope(deliveryTag=2 , redeliver=false, exchange=test_ack_exchange, routingKey=ack.go)
     *  properties :#contentHeader<basic>(content-type=null, content-encoding=UTF-8, headers={num=1}, delivery-mode=2, priority=null, correlation-id=null,
     * reply-to=null, expiration=null, message-id=null, timestamp=null, type=null, user-id=null, app-id=null, cluster-id=null)
     * @param consumerTag
     * @param envelope
     * @param properties
     * @param body
     * @throws IOException
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        logger.info(" ------  消费者receive 信息 ------");
        logger.info("consumerTag :{}",consumerTag);
        logger.info("envelope :{}",envelope);
        logger.info("properties :{}",properties);
        logger.info("body :{}",new String(body));
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
