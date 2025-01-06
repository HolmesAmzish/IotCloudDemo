package cn.arorms.iot.sender.entity;

/**
 * Message entity for sending
 * @version 1.0 2024-01-06
 * @since 2025-01-06
 */
public class Message {
    String username;
    String type;
    String content;

    public Message(String username, String type, String content) {
        this.username = username;
        this.type = type;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
