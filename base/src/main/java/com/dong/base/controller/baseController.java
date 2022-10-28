package com.dong.base.controller;

import com.dong.base.service.SendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class baseController {

    @Resource
    SendService sendService;
    /**
     * 1.服务端发送消息给rabbitMQ
     */
    @RequestMapping("/sendRabbit")
    public void sendToRabbitMQ(@RequestParam("centent") String centent){
        sendService.sendToRabbitMQ(centent);

    }
}
