package cn.arorms.iot.server.mapper;

import cn.arorms.iot.server.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MessageMapper
 * @version 1.0 2025-01-07
 * @since 2025-01-07
 */
@Mapper
public interface MessageMapper {
    void insertMessage(Message message);
    List<Message> getAllMessage();
    Message getMessageById(int id);
    List<Message> getMessagesByPage(int offset, int pageSize);
}
