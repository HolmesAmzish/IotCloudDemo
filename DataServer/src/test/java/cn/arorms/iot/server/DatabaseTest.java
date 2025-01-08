package cn.arorms.iot.server;

import cn.arorms.iot.server.entity.Message;
import cn.arorms.iot.server.mapper.MessageMapper;
import cn.arorms.iot.server.service.MessageService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * DatabaseTest
 * @version 1.0 2025-01-07
 * @since 2025-01-07
 */
public class DatabaseTest {
    @Test
    public void test() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);

            Message message = new Message("test_user", "text", "Hello1");

            mapper.insertMessage(message);
            session.commit();
            System.out.println("Message inserted");
        }
    }

    @Test
    public void testGetMessage() throws IOException {
        MessageService messageService = new MessageService();
        List<Message> messageList = messageService.getAllMessage();
        System.out.println(messageList.get(0).toString());
    }

    @Test
    public void testGetMessageById() throws IOException {
        MessageService messageService = new MessageService();
        Message message = messageService.getMessageById(1);
        System.out.println(message.toString());
    }
}