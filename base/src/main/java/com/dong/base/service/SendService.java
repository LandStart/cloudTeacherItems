package com.dong.base.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;

@Service
public class SendService {

    public void sendToRabbitMQ(String centent){

        ConnectionFactory cf = new ConnectionFactory();

        //配置客户端连接参数
        HashMap<String, Object> clientProperties = new HashMap<>();
        clientProperties.put("connection_name", "producer");
        cf.setClientProperties(clientProperties);

        // 将心跳超时时间设置为60秒
        cf.setRequestedHeartbeat(60);


    }
}
