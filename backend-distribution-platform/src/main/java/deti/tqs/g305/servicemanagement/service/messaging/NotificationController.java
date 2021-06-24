package deti.tqs.g305.servicemanagement.service.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate template;
    
    @MessageMapping("/notification")
    @SendTo("/contract")
    public String send(final String message){
        this.template.convertAndSend("/contract", message);
        return message;
    }
}

