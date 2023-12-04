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

    /**
     * Generates a response map containing a message and its timestamp.
     *
     * This method creates a HashMap with the given message and the current timestamp,
     * formatted as specified by TIME_FORMATTER and adjusted for AM/PM notation.
     *
     * @param message The message to be included in the response.
     * @return A HashMap with keys "message" and "timestamp", holding the message and its formatted timestamp.
     */
    public HashMap<String, String> generateResponse(String message){
        HashMap<String, String> response = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();

        response.put("message", message);
        response.put("timestamp", timestamp.format(TIME_FORMATTER).replace("a.m.", "AM").replace("p.m.","PM"));

        return response;
    }

    /**
     * Saves a message to the database and associates it with a session.
     *
     * This method creates a new Message object using the provided content, session ID, and sender,
     * and saves it to the message repository. It then attempts to add this message to the specified session.
     * Any exceptions during the process are caught and their messages are printed to the console.
     *
     * @param content The content of the message to be saved.
     * @param sessionId The ID of the session to which the message will be added.
     * @param sender The sender of the message.
     */
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
