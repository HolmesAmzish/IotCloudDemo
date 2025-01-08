package cn.arorms.iot.server.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Message entity
 * @version 1.0 2025-01-07
 * @since 2025-01-07
 * @author Holmes Amzish
 */
@Data
public class Message {
    public Integer id;
    public String username;
    public String type;
    public String content;
    public LocalDateTime time;

    public Message() {

    }

    public Message(String username, String type, String content) {
        this.username = username;
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
