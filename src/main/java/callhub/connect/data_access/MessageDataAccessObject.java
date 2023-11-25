package callhub.connect.data_access;

import callhub.connect.use_case.message.MessageDataAccessInterface;
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
    final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss a", Locale.CANADA);

    public MessageDataAccessObject(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public HashMap<String, String> generateResponse(String message){
        HashMap<String, String> response = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();

        response.put("message", message);
        response.put("timestamp", timestamp.format(TIME_FORMATTER).replace("a.m.", "AM").replace("p.m.","PM"));

        return response;
    }
}
