package com.rabbitmq.publish;

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
        channel.exchangeDeclare("pubsub-exchange", BuiltinExchangeType.FANOUT);
        channel.queueBind("pubsub-queue1","pubsub-exchange","");
        channel.queueBind("pubsub-queue2","pubsub-exchange","");

        // 4、发布消息到Exchange

        for (int i = 0; i < 10; i++) {
            String msg = "HELLO-WORLD !" + i;
            channel.basicPublish("pubsub-exchange","Work",null,msg.getBytes());
        }
        //exchange是不会帮你把消息持久化到本地的
        System.out.println("生产者发布消息。。。。。");
        // 5、释放资源
        channel.close();
        connection.close();
    }
}
