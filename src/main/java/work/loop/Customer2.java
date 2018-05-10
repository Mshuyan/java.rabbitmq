package work.loop;

import com.rabbitmq.client.*;
import utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer2 {
    //使用的队列名称
    private static final String QUEUE_NAME = "work_test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //使用ConnectionUtil工具类获取连接
        Connection con = ConnectionUtil.getConnection();
        //在连接中获取1个通道
        Channel channel = con.createChannel();
        //在通道中声名1个队列
        //启动消息持久化
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        //创建consumer对象，并重写接收到消息的回调函数
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("[2]"+message);
            }
        };
        //使用consumer对象监听通道
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
