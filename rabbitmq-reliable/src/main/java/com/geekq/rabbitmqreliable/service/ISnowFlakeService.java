package com.geekq.rabbitmqreliable.service;

/**
 * @author 邱润泽 bullock
 */
public interface ISnowFlakeService {

    long getSnowFlakeID();

    long[] getSnowFlakeIDs(int size);
}
