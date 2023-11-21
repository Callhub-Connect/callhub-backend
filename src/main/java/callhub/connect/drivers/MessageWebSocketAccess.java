package callhub.connect.drivers;
import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class MessageWebSocketAccess {

    private static final Gson gson = new Gson();

    @MessageMapping("/message-customer/{sessionId}")
    @SendTo("/topic/message-employee/{sessionId}")
    public String sendMessageCustomer(@DestinationVariable String sessionId, String message, String timestamp) throws Exception {
        System.out.println(message);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("timestamp", timestamp);

        return gson.toJson(response);
    }

    @MessageMapping("/message-employee/{sessionId}")
    @SendTo("/topic/message-customer/{sessionId}")
    public String sendMessageEmployee(@DestinationVariable String sessionId, String message, String timestamp) throws Exception {
        System.out.println(message);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("timestamp", timestamp);
        return gson.toJson(response);
    }

}