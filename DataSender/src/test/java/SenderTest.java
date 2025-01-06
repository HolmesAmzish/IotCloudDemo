import cn.arorms.iot.sender.MqttSender;
import cn.arorms.iot.sender.entity.Message;
import org.junit.jupiter.api.Test;

public class SenderTest {
    @Test
    public void testSend() {
        MqttSender sender = new MqttSender();
        Message msg = new Message("Cacc", "text", "Hello World");
        sender.sendMessage(msg);
    }
}
