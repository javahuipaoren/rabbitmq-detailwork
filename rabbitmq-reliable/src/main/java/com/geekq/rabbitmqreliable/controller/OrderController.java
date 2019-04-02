package com.geekq.rabbitmqreliable.controller;

import com.geekq.rabbitmqreliable.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 邱润泽 bullock
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    @ResponseBody
    public String createOrder(){

        orderService.createOrder("订单超级一号");

        return "gogo";
    }
}
