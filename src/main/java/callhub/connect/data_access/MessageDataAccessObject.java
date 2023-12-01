package callhub.connect.data_access;

import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import callhub.connect.entities.Session;
import callhub.connect.use_case.message.MessageDataAccessInterface;
import callhub.connect.use_case.session.SessionDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

@Service
public class MessageDataAccessObject implements MessageDataAccessInterface {

    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final SessionDataAccessInterface sessionDataAccessInterface;
    final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss a", Locale.CANADA);

    public MessageDataAccessObject(MessageRepository messageRepository, SessionDataAccessInterface sessionDataAccessInterface) {
        this.messageRepository = messageRepository;
        this.sessionDataAccessInterface = sessionDataAccessInterface;
    }

    public HashMap<String, String> generateResponse(String message){
        HashMap<String, String> response = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();

        response.put("message", message);
        response.put("timestamp", timestamp.format(TIME_FORMATTER).replace("a.m.", "AM").replace("p.m.","PM"));

        return response;
    }

    public void sendResponseToDatabase(String content, String sessionId, Sender sender) {
        Message message = new Message(content, sessionId, sender);
        Message result = messageRepository.save(message);
        // add message to session
        try{
            sessionDataAccessInterface.addMessageToSession(sessionId, result);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
