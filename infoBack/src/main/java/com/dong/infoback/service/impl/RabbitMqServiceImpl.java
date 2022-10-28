package com.dong.infoback.service.impl;

import com.dong.infoback.service.rabbitMqService;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMqServiceImpl implements rabbitMqService {

    @Value("${rabbitMq.host}")
    private String host;
    @Value("${rabbitMq.portStr}")
    private String portStr;
    @Value("${rabbitMq.user}")
    private String user;
    @Value("${rabbitMq.password}")
    private String password;

    public List<String>  receMq(String queue) throws Exception {
        Connection connection = null;
        Channel channel = null;
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(Integer.valueOf(portStr));
            factory.setUsername(user);
            factory.setPassword(password);
             connection = factory.newConnection();
             channel = connection.createChannel();
            
            channel.queueDeclare(queue, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            List messagesList =new ArrayList();
            Consumer consumer = new DefaultConsumer(channel)
            {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException
                {
                    String message = new String(body, "UTF-8");
                    messagesList.add(message);
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queue, true, consumer);

            return messagesList;
        }
        catch (Exception e){
            throw new Exception("error");
        }finally{

        }

    }


    public static void main2(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.46.192.17");
        factory.setPort(5672);

        factory.setUsername("root");
        factory.setPassword("13772445559ldnnm~");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("hello", false, false, false, null);

        String message = "Hello World test!";
        channel.basicPublish("", "hello", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.46.192.17");
        factory.setPort(5672);

        factory.setUsername("root");
        factory.setPassword("13772445559ldnnm~");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("hello", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException
            {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume("hello", true, consumer);
    }
}
