package work.fair;

import com.rabbitmq.client.*;
import utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer1 {
    //使用的队列名称
    private static final String QUEUE_NAME = "work_test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //使用ConnectionUtil工具类获取连接
        Connection con = ConnectionUtil.getConnection();
        //在连接中获取1个通道
        final Channel channel = con.createChannel();
        //在通道中声名1个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //限定1个消费者每次只处理1条消息，消费者应答之前不再发送下一条消息
        channel.basicQos(1);
        //创建consumer对象，并重写接收到消息的回调函数
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("[1]"+message);
                //手动应答
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //使用consumer对象监听通道
        //关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
