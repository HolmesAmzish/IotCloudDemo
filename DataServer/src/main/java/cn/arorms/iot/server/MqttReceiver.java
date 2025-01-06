package cn.arorms.iot.server;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.nio.charset.StandardCharsets;

/**
 * MqttReceiver running on server
 * Receive sender message from Aliyun Iot server
 * @version 1.0 2025-01-06
 * @since 2025-01-06
 * @author Holmes Amzish
 */
public class MqttReceiver {

    private static final String PRODUCT_KEY = "k0zisw6ZO1s";
    private static final String DEVICE_NAME = "Receiver";
    private static final String REGION_ID = "cn-shanghai";
    private static final int PORT = 1883;

    public static void main(String[] args) {
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

            // 设置回调
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received message from [" + topic + "]: " + new String(message.getPayload(), StandardCharsets.UTF_8));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 发布消息完成后的回调，这里可以忽略
                }
            });

            // 连接到代理
            client.connect(options);
            System.out.println("MQTT 接收端连接成功！");

            // Subscribe topic
            String subscribeTopic = "/" + PRODUCT_KEY + "/" + DEVICE_NAME + "/user/get";
            client.subscribe(subscribeTopic);
            System.out.println("已订阅主题: " + subscribeTopic);

            // Keep listening
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}