package com.rabbitmq.work;

import com.rabbitmq.client.*;
import com.rabbitmq.config.RabbitMQClient;
import org.junit.Test;

import java.io.IOException;

public class Consumer2 {
    @Test
    public void Consum() throws Exception {
        //获取连接
        Connection connection = RabbitMQClient.getConnection();
        //创建channel
        final Channel channel = connection.createChannel();
        //声明队列
        /**
         * Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
         *                                  Map<String, Object> arguments) throws IOException;
         *
         *   queue: 指定队列的名称
         *   durable: 当前队列是否需要持久化
         *   exclusive: 是否排外（conn.close() 当前队列会被自动删除，当前队列只能被一个消费者消费）
         *   autoDelete: 如果这个队列没有消费者在消费，队列会自动删除
         *   arguments: 指定当前队列的其它信息
         *
         */
        channel.queueDeclare("Hello-Work",true,false,false,null);

        /**
         * 指定当前消费者一次性消费多少个消息
         *
         */
        channel.basicQos(1);

        //开启监听Queue
        /**
         * String basicConsume(String queue, DeliverCallback deliverCallback,
         *                        CancelCallback cancelCallback) throws IOException;
         *
         *    queue：指定要消费哪个队列
         *    deliverCallback：指定是否自动ACK（true，接收e到消息后会立即告诉RabbitMQ ）
         *    cancelCallback：指定消费回调
         */
        DefaultConsumer consume = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者2接收到消息： "+ new String(body,"UTF-8"));

                // 手动ACK
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume("Hello-Work",false,consume);

        System.out.println("消费者2监听队列。。。。。。。。。。");
        System.in.read();
        //释放资源
        channel.close();
        connection.close();

    }
}
