package cn.arorms.iot.server;

import cn.arorms.iot.server.entity.Message;
import cn.arorms.iot.server.service.MessageService;
import cn.arorms.iot.server.websocket.WebSocketServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * MQTT Receiver
 * @version 1.0 2025-01-09
 * @since 2025-01-06
 */
@Component
@Slf4j
public class MqttReceiver {

    private final MessageService messageService;

    private static final String PRODUCT_KEY = "k0zisw6ZO1s";
    private static final String DEVICE_NAME = "Receiver";
    private static final String REGION_ID = "cn-shanghai";
    private static final int PORT = 1883;

    public MqttReceiver(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostConstruct
    public void init() {
        try {
            long timestamp = System.currentTimeMillis();
            String clientId = "k0zisw6ZO1s.Receiver|securemode=2,signmethod=hmacsha256,timestamp=1736176155489|";
            String username = String.format("%s&%s", DEVICE_NAME, PRODUCT_KEY);
            String password = "9608df3e9abe09c43d0c4c59329790b43a797abf18e619a6e8bb949a1eb83815";

            String broker = "ssl://" + PRODUCT_KEY + ".iot-as-mqtt." + REGION_ID + ".aliyuncs.com:" + PORT;

            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    log.error("connectionLost", cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                    log.info("Received message from [{}]: {}", topic, payload);

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    try {
                        Message msg = objectMapper.readValue(message.getPayload(), Message.class);
                        msg.setTime(LocalDateTime.now());
                        messageService.insertMessage(msg);
                        String formattedMessage = objectMapper.writeValueAsString(msg);
                        // 推送到WebSocket
                        log.info("Sending message to WebSocket: {}", formattedMessage);
                        WebSocketServer.sendMessageToAll(formattedMessage);
                    } catch (Exception e) {
                        log.error("Error parsing message to Message object: {}", e.getMessage());
                    }
                }


                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });

            client.connect(options);
            log.info("MQTT connect successful!");

            // Subscribe topic
            String subscribeTopic = "/" + PRODUCT_KEY + "/" + DEVICE_NAME + "/user/get";
            client.subscribe(subscribeTopic);
            log.info("Topic subscribed: {}", subscribeTopic);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
