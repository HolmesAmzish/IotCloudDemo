package cn.arorms.iot.server.entity;

import java.time.LocalDateTime;

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
