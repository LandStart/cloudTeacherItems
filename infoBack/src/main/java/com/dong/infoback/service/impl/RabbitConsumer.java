package com.dong.infoback.service.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.*;
import java.io.IOException;


public class RabbitConsumer extends DefaultConsumer {

    public RabbitConsumer(Channel channel) {
        super(channel);
    }

    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                               byte[] body)
                        throws IOException
        {
            String message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

        }

}
