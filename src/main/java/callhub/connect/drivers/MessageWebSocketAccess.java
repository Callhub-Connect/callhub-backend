package callhub.connect.drivers;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//@Controller
public class MessageWebSocketAccess {
    String sessionId;
    String sessionCode;

    public MessageWebSocketAccess(String sessionId, String sessionCode) {
        this.sessionId = sessionId;
        this.sessionCode = sessionCode;
    }

    @MessageMapping("/message-customer/{sessionId}")
    @SendTo("/topic/message-employee/{sessionId}")
    public String sendMessageCustomer(@DestinationVariable String sessionId, String message) throws Exception {
        System.out.println("message");
        return message + " (" + sessionId + ")";
    }

    @MessageMapping("/message-employee/{sessionId}")
    @SendTo("/topic/message-customer/{sessionId}")
    public String sendMessageEmployee(@DestinationVariable String sessionId, String message) throws Exception {
        System.out.println("message");
        return message + " (" + sessionId + ")";
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String sendMessage(String message) throws Exception {
        System.out.println("message");
        return message + " received";
    }
}