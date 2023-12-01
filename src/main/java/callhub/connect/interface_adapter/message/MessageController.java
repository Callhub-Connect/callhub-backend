package callhub.connect.interface_adapter.message;

import callhub.connect.entities.Sender;
import callhub.connect.use_case.message.MessageInputBoundary;
import callhub.connect.use_case.message.MessageInputData;
import callhub.connect.use_case.message.MessageInteractor;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MessageController {
    private static final Gson gson = new Gson();

    @Autowired
    private MessageInputBoundary messageInteractor;

    @MessageMapping("/message-customer/{sessionId}")
    @SendTo("/topic/message-employee/{sessionId}")
    public String sendMessageCustomer(@DestinationVariable String sessionId, String message) throws Exception {
        MessageInputData inputData = new MessageInputData(sessionId, message, Sender.CUSTOMER);
        HashMap<String, String> response = messageInteractor.newMessage(inputData);
        return gson.toJson(response);
    }

    @MessageMapping("/message-employee/{sessionId}")
    @SendTo("/topic/message-customer/{sessionId}")
    public String sendMessageEmployee(@DestinationVariable String sessionId, String message) throws Exception {
        MessageInputData inputData = new MessageInputData(sessionId, message, Sender.EMPLOYEE);
        HashMap<String, String> response = messageInteractor.newMessage(inputData);
        return gson.toJson(response);
    }

    @MessageMapping("/end-session/{sessionId}")
    @SendTo("/topic/end-session/{sessionId}")
    public String endSession(@DestinationVariable String sessionId) throws Exception {
        return "session ended";
    }
}
