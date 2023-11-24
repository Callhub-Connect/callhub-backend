package callhub.connect.drivers;
import callhub.connect.data_access.MessageDataAccess;
import callhub.connect.data_access.MessageRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import callhub.connect.use_case.MessageInteractor;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import java.util.HashMap;
import java.util.Locale;

@Controller
public class MessageWebSocketAccess {

    private static final Gson gson = new Gson();
    private MessageInteractor messageInteractor = new MessageInteractor();
    @Autowired
    private MessageDataAccess messageDataAccess;

    @MessageMapping("/message-customer/{sessionId}")
    @SendTo("/topic/message-employee/{sessionId}")
    public String sendMessageCustomer(@DestinationVariable String sessionId, String message) throws Exception {
        messageDataAccess.sendResponseToDatabase(message, sessionId, Sender.CUSTOMER);
        HashMap<String, String> response = messageInteractor.generateResponse(message);
        return gson.toJson(response);
    }

    @MessageMapping("/message-employee/{sessionId}")
    @SendTo("/topic/message-customer/{sessionId}")
    public String sendMessageEmployee(@DestinationVariable String sessionId, String message) throws Exception {
        messageDataAccess.sendResponseToDatabase(message, sessionId, Sender.EMPLOYEE);
        HashMap<String, String> response = messageInteractor.generateResponse(message);
        return gson.toJson(response);
    }



}