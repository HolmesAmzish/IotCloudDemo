import cn.arorms.iot.sender.MqttSender;
import cn.arorms.iot.sender.entity.Message;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;
import java.io.File;
import java.io.FileInputStream;

public class SenderTest {
    @Test
    public void testSend() {
        MqttSender sender = new MqttSender();
        Message msg = new Message("test_sender", "text", "Hello World");
        sender.sendMessage(msg);
    }

    @Test
    public void testSendImage() {
        String imagePath = "E:/Images/icon.jpg";
        String imageBase64 = convertImageToBase64(imagePath);
        MqttSender sender = new MqttSender();
        Message msg = new Message("test_sender", "image", imageBase64);
        sender.sendMessage(msg);
    }


    private String convertImageToBase64(String imagePath) {
        File file = new File(imagePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] imageBytes = new byte[(int) file.length()];
            fileInputStream.read(imageBytes);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
