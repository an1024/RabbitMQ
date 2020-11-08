package com.rabbitmq.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.config.RabbitMQClient;
import org.junit.Test;

public class Publisher {
    @Test
    public void publish() throws Exception {
        // 1、获取连接
        Connection connection = RabbitMQClient.getConnection();
        // 2、创建Channel
        Channel channel = connection.createChannel();

        // 3、创建exchange 绑定某一个队列
        channel.exchangeDeclare("routing-exchange", BuiltinExchangeType.DIRECT);
        channel.queueBind("routing-queue-error","routing-exchange","ERROR");
        channel.queueBind("routing-queue-info","routing-exchange","INFO");

        // 4、发布消息到Exchange
        channel.basicPublish("routing-exchange","ERROR",null,"ERROR1".getBytes());
        channel.basicPublish("routing-exchange","ERROR",null,"ERROR2".getBytes());
        channel.basicPublish("routing-exchange","INFO",null,"INFO1".getBytes());
        channel.basicPublish("routing-exchange","ERROR",null,"ERROR3".getBytes());
        channel.basicPublish("routing-exchange","INFO",null,"INFO2".getBytes());
        channel.basicPublish("routing-exchange","ERROR",null,"ERROR4".getBytes());

        //exchange是不会帮你把消息持久化到本地的
        System.out.println("生产者发布消息。。。。。");
        // 5、释放资源
        channel.close();
        connection.close();
    }
}
