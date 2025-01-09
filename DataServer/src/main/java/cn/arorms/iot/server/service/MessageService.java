package cn.arorms.iot.server.service;

import cn.arorms.iot.server.mapper.MessageMapper;
import cn.arorms.iot.server.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Message Service
 * @version 1.0 2025-01-07
 * @since 2025-01-07
 */
@Service
@Slf4j
public class MessageService {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize SqlSessionFactory", e);
        }
    }

    public void insertMessage(Message msg) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            mapper.insertMessage(msg);
            session.commit();
            log.info("Message inserted");
        }
    }

    public List<Message> getAllMessage() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            List<Message> messageList = mapper.getAllMessage();
            log.info("All message got.");
            return messageList;
        }
    }

    public Message getMessageById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            Message message = mapper.getMessageById(id);
            log.info(String.format("Message No.%d got.", id));
            return message;
        }
    }

    public List<Message> getMessagesByPage(int page, int pageSize) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            int offset = (page - 1) * pageSize; // 计算起始行
            return mapper.getMessagesByPage(offset, pageSize);
        }
    }
}

