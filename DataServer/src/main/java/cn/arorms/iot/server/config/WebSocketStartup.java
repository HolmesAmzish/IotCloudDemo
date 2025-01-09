package cn.arorms.iot.server.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.arorms.iot.server.websocket.WebSocketServer;

@Configuration
public class WebSocketStartup {

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            WebSocketServer.sendMessageToAll("WebSocket Server started!");
        };
    }
}
