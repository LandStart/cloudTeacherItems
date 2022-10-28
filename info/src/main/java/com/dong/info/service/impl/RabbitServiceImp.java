package com.dong.info.service.impl;

import com.dong.info.service.rabbitService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RabbitServiceImp implements rabbitService {


    @Value("${rabbitMq.host}")
    private String host;
    @Value("${rabbitMq.portStr}")
    private String portStr;
    @Value("${rabbitMq.user}")
    private String user;
    @Value("${rabbitMq.password}")
    private String password;



    public  String sendToMq(String queueName,String content) throws Exception {
            Connection connection = null;
            Channel channel = null;
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(host);
                factory.setPort(Integer.valueOf(portStr));
                factory.setUsername(user);
                factory.setPassword(password);

                connection = factory.newConnection();
                channel = connection.createChannel();
                //channel.exchangeDeclare("","topic",true);
                channel.queueDeclare(queueName, false, false, false, (Map) null);

                String message = content;
                channel.basicPublish("", queueName, (AMQP.BasicProperties) null, message.getBytes("UTF-8"));

                System.out.println(" [x] Sent '" + message + "'");

                return "ok";
            }
            catch (Exception e) {
                throw new Exception("error");
            }
    }

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("120.46.192.17");
            factory.setPort(Integer.valueOf("5672"));
            factory.setUsername("root");
            factory.setPassword("13772445559ldnnm~");

            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare("helloTest", false, false, false, (Map) null);

            String message = "hello test";
            channel.basicPublish("", "hello", (AMQP.BasicProperties) null, message.getBytes("UTF-8"));

            System.out.println(" [x] Sent '" + message + "'");
        }
        catch (Exception e) {
            throw new Exception("error");
        }finally{
            channel.close();
            connection.close();
        }
    }

}

