package com.rabbitmq.work;

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
        //发布消息到Exchange
        /**
         * 参数1：指定exchange，使用""
         * 参数2:指定路由规制，使用具体的队列名称
         * 参数3：指定传递的消息所携带的properties
         * 参数4：指定发布的具体消息，byte[]类型
         */
        for (int i = 0; i < 20; i++) {
            String msg = "HELLO WORLD !" + i;
            channel.basicPublish("","Hello-Work",null,msg.getBytes());
        }
        //exchange是不会帮你把消息持久化到本地的
        System.out.println("生产者发布消息。。。。。");
        //释放资源
        channel.close();
        connection.close();
    }
}
