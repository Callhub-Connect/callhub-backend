package callhub.connect.controllers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String sendMessage(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("message");
        return message + "recieved";
    }

}