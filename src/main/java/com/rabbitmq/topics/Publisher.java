package com.rabbitmq.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.config.RabbitMQClient;
import org.junit.Test;

public class Publisher {
    @Test
    public void publish() throws Exception {
        //获取连接
        Connection connection = RabbitMQClient.getConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        //* 占位符
        // # 通配符
        channel.exchangeDeclare("topics-exchange", BuiltinExchangeType.TOPIC);
        channel.queueBind("topics-queue-1","topics-exchange","*.red.*");
        channel.queueBind("topics-queue-2","topics-exchange","fast.#");
        channel.queueBind("topics-queue-2","topics-exchange","*.*.rabbit");


        //发布消息到Exchange
        channel.basicPublish("topics-exchange","fast.red.monkey",null,"红快猴子".getBytes());
        channel.basicPublish("topics-exchange","slow.black.dog",null,"黑慢狗".getBytes());
        channel.basicPublish("topics-exchange","fast.white.cat",null,"快白猫".getBytes());


        System.out.println("生产者发布消息。。。。。");
        //释放资源
        channel.close();
        connection.close();
    }
}
