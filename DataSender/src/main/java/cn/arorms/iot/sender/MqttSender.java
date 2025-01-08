package cn.arorms.iot.sender;

import cn.arorms.iot.sender.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.nio.charset.StandardCharsets;

/**
 * MqttSender
 * Send message object to mqtt server
 * @version 1.0 2025-01-07
 * @since 2025-01-06
 * @author Cacc
 */
public class MqttSender {

    private static final String PRODUCT_KEY = "k0zisw6ZO1s";
    private static final String DEVICE_NAME = "Sender";

    long timestamp = System.currentTimeMillis();
    private static final String clientId = "k0zisw6ZO1s.Sender|securemode=2,signmethod=hmacsha256,timestamp=1736176526291|";
    private static final String username = "Sender&k0zisw6ZO1s";
    private static final String password = "d4cfa49e944161c2c38cfb88b4cf66a63c9a68082c5a0f36287c01d75aa136ef";
    private static final String broker = "tcp://k0zisGb5t6q.iot-as-mqtt.cn-shanghai.aliyuncs.com:1883";

    public static void sendMessage(Message msg) {
        try {

            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);

            client.connect(options);
            System.out.println("MQTT sender connected!");

            String topic = "/" + PRODUCT_KEY + "/" + DEVICE_NAME + "/user/update";

            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(msg);
            MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
            message.setQos(0);

            client.publish(topic, message);
            System.out.println("Message published!");

            client.disconnect();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}