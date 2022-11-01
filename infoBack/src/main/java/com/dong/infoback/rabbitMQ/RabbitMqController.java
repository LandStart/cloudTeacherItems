package com.dong.infoback.rabbitMQ;

import com.dong.infoback.service.impl.RabbitMqServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RabbitMqController {

    @Resource
    RabbitMqServiceImpl rabbitMqService;

    @RequestMapping("receMq")
    public List<String> receMq(@RequestParam("queue") String queue) throws Exception {
        return rabbitMqService.receMq(queue);
    }
}
