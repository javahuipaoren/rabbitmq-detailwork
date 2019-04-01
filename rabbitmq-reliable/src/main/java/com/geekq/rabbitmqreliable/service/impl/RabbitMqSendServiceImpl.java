package com.geekq.rabbitmqreliable.service.impl;

import com.geekq.rabbitmqreliable.constants.Constants;
import com.geekq.rabbitmqreliable.entity.BrokerMessageLog;
import com.geekq.rabbitmqreliable.entity.BrokerMessageLogDlx;
import com.geekq.rabbitmqreliable.mapper.BrokerMessageLogDlxMapper;
import com.geekq.rabbitmqreliable.mapper.BrokerMessageLogMapper;
import com.geekq.rabbitmqreliable.service.ISnowFlakeService;
import com.geekq.rabbitmqreliable.service.RabbitMqSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

/**
 * @author 邱润泽 bullock
 */
public class RabbitMqSendServiceImpl implements RabbitMqSendService {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqSendServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private BrokerMessageLogDlxMapper brokerMessageLogDlxMapper;

    @Value("${rabbitmq.listener.order.exchange.name}")
    private String exchangeName ;
    @Value("${rabbitmq.listener.order.key}")
    private String rkey;
    /**    回调函数: confirm确认
     *     失败 2 重试
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("correlationData: {}", correlationData);
            String messageId = correlationData.getId();
            if(ack){
                //如果confirm返回成功 则进行更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS,new Date());
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                log.error("消息发送失败，需要进行异常处理... ");
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_FAILURE,new Date());
            }
        }
    };

    /**
     * 消息 未达 如没有队列 没有 路由 等等 ..... 消息不可达 进行 操作
     *
     * 消息报警 并记录消息 直接 记录在 废弃mq数据库 根据自身业务
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode,
                                    String replyText, String exchange, String routingKey) {

            log.info("message :{} ,replyCode :{} , replyText:{},exchange:{},routingKey:{}");

            BrokerMessageLogDlx brokerMessageLogDlx =new BrokerMessageLogDlx() ;
            brokerMessageLogDlx.setCreateTime(new Date());
            brokerMessageLogDlx.setUpdateTime(new Date());
            brokerMessageLogDlx.setStatus(Constants.ORDER_SEND_FAILURE);
            brokerMessageLogDlxMapper.insert(brokerMessageLogDlx);
            //TOdo debug 看看

        }
    } ;
    @Override
    public void send(BrokerMessageLog messageLog) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        /**
         * 发送订单消息
         */
        CorrelationData correlationData = new CorrelationData(messageLog.getMessageId());
        rabbitTemplate.convertAndSend(exchangeName,rkey,messageLog,correlationData);
    }
}
