package cn.arorms.iot.server.controller;

import cn.arorms.iot.server.entity.Message;
import cn.arorms.iot.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * MessageController
 * @version 1.0 2025-01-09
 * @since 2025-01-07
 * @author Holmes Amzish
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String message(Model model) {
        return "messages";
    }

    @GetMapping("/history")
    public String printMessageByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int pageSize,
            Model model) {
        List<Message> messages = messageService.getMessagesByPage(page, pageSize);
        model.addAttribute("messages", messages);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("prevPage", Math.max(page - 1, 1));
        return "history";
    }
}
