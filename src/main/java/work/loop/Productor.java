package work.loop;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Productor {
    //使用的队列名称
    private static final String QUEUE_NAME = "work_test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //使用ConnectionUtil工具类获取连接
        Connection con = ConnectionUtil.getConnection();
        //在连接中获取1个通道
        Channel channel = con.createChannel();
        //在通道中声名1个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义待发送消息
        String msg = "hello rabbitmq work test";
        //向队列中发送消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("send");
        //关闭连接和通道
        channel.close();
        con.close();
    }
}
