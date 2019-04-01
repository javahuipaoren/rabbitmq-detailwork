package com.geekq.rabbitmqreliable.service.impl;

import com.geekq.rabbitmqreliable.constants.ResultStatus;
import com.geekq.rabbitmqreliable.exception.GlobalException;
import com.geekq.rabbitmqreliable.service.ISnowFlakeService;
import com.geekq.rabbitmqreliable.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author 邱润泽 bullock
 */
@Service
public class SnowFlakeServiceImpl implements ISnowFlakeService {
    private final  static Logger log = LoggerFactory.getLogger(SnowFlakeServiceImpl.class);

    @Override
    public long getSnowFlakeID() {
        long id = SnowFlake.getId();
        log.info("id: {}", id);
        return id;
    }

    @Override
    public long[] getSnowFlakeIDs(int size) {
        if (size < 1) {
            throw new GlobalException(ResultStatus.FAILD);
        }
        long[] ids = new long[size];
        for (int i = 0; i < size; i++) {
            long id = SnowFlake.getId();
            ids[i] = id;
            log.info("id: {}", id);
        }
        return ids;
    }

}
