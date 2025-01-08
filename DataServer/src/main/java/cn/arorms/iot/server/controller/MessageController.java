package cn.arorms.iot.server.controller;

import cn.arorms.iot.server.entity.Message;
import cn.arorms.iot.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * MessageController
 * @version 1.0 2025-01-07
 * @since 2025-01-07
 * @author Holmes Amzish
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    // 显示所有消息
    @GetMapping("/messages")
    public String message(Model model) {
        List<Message> messages = messageService.getAllMessage();
        model.addAttribute("messages", messages);
        return "messages";  // 对应 messages.html
    }

    // 显示单条消息
    @GetMapping("/message/{id}")
    public String printMessageById(Model model, @PathVariable int id) {
        Message message = messageService.getMessageById(id);
        model.addAttribute("message", message);
        return "messages";  // 对应 messages.html
    }
}
