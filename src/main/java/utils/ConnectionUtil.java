package utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义工厂类
        ConnectionFactory factory = new ConnectionFactory();
        //设置要连接的rabbitmq服务器IP
        factory.setHost("127.0.0.1");
        //设置AMQP协议的端口号
        factory.setPort(5672);
        //设置要连接的虚拟主机
        factory.setVirtualHost("/vhost_test");
        //设置该虚拟主机授权的1个账户和密码
        factory.setUsername("shuyan");
        factory.setPassword("943397");
        //在工厂中获取1个新的Connection对象并返回
        return factory.newConnection();
    }
}
