package com.dong.info.rabbit;

import com.dong.info.service.impl.RabbitServiceImp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class rabbitMQController {

    @Resource
    RabbitServiceImp rabbitServiceImp;

    @RequestMapping("Send")
    public String send(@RequestParam("queueName") String queueName,@RequestParam("content") String content) throws Exception {
        return rabbitServiceImp.sendToMq(queueName,content);
    }
}
