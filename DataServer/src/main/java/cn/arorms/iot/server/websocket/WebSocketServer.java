package cn.arorms.iot.server.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket/WebSocketServer
 * @version 0.1 2025-01-08
 * @since 2025-01-08
 * @author Holmes Amzish
 */
@Slf4j
@Component
@ServerEndpoint("/websocket")
public class WebSocketServer {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        log.info("New connection, session id: {}", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        log.info("Connection closed, session id: {}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Received message from client [{}]: {}", session.getId(), message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Error occurred in session [{}]: {}", session.getId(), error.getMessage());
    }

    public static void sendMessageToAll(String message) {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("Error sending message: {}", e.getMessage());
            }
        }
    }
}
