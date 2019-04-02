package com.geekq.rabbitmqreliable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.geekq.rabbitmqreliable.mapper")
public class RabbitmqReliableApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqReliableApplication.class, args);
    }

}
