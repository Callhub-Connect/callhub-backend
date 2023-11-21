package callhub.connect.drivers;
import callhub.connect.data_access.MessageRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

@Controller
public class MessageWebSocketAccess {

    @Autowired
    private MessageRepository messageRepository;
    private static final Gson gson = new Gson();
    final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss a", Locale.CANADA);

    @MessageMapping("/message-customer/{sessionId}")
    @SendTo("/topic/message-employee/{sessionId}")
    public String sendMessageCustomer(@DestinationVariable String sessionId, String message) throws Exception {
        HashMap<String, String> response = generateResponse(message);
        sendResponseToDatabase(message, sessionId, Sender.CUSTOMER);
        return gson.toJson(response);
    }

    @MessageMapping("/message-employee/{sessionId}")
    @SendTo("/topic/message-customer/{sessionId}")
    public String sendMessageEmployee(@DestinationVariable String sessionId, String message) throws Exception {
        HashMap<String, String> response = generateResponse(message);
        sendResponseToDatabase(message, sessionId, Sender.EMPLOYEE);
        return gson.toJson(response);
    }

    private HashMap<String, String> generateResponse(String message){
        HashMap<String, String> response = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();

        response.put("message", message);
        response.put("timestamp", timestamp.format(TIME_FORMATTER).replace("a.m.", "AM").replace("p.m.","PM"));

        return response;
    }

    private void sendResponseToDatabase(String content, String sessionId, Sender sender){
        Message message = new Message(content, sessionId, sender);
        messageRepository.save(message);
    }

}