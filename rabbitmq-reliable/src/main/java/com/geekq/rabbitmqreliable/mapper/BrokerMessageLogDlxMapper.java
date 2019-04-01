package com.geekq.rabbitmqreliable.mapper;

import com.geekq.rabbitmqreliable.entity.BrokerMessageLogDlx;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BrokerMessageLogDlxMapper {
    int deleteByPrimaryKey(String messageId);

    int insert(BrokerMessageLogDlx record);

    int insertSelective(BrokerMessageLogDlx record);

    BrokerMessageLogDlx selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(BrokerMessageLogDlx record);

    int updateByPrimaryKey(BrokerMessageLogDlx record);

    /**
     * 查询消息状态为0(发送中) 且已经超时的消息集合
     * @return
     */
	List<BrokerMessageLogDlx> query4StatusAndTimeoutMessage();
	
	/**
	 * 重新发送统计count发送次数 +1
	 * @param messageId
	 * @param updateTime
	 */
	void update4ReSend(@Param("messageId") String messageId, @Param("updateTime") Date updateTime);

	/**
	 * 更新最终消息发送结果 成功 or 失败
	 * @param messageId
	 * @param status
	 * @param updateTime
	 */
	void changeBrokerMessageLogDlxStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);


	
}