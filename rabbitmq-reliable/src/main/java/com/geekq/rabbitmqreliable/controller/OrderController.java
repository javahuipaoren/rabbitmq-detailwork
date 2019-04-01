package com.geekq.rabbitmqreliable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 邱润泽 bullock
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {



    @RequestMapping("/create")
    @ResponseBody
    public String createOrder(){


        return null;
    }
}
