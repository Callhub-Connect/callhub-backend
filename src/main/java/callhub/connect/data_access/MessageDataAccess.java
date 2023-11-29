package callhub.connect.data_access;

import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class MessageDataAccess {

    private ApplicationContext context;
    private MessageRepository messageRepository;

    public MessageDataAccess(ApplicationContext context) {
        this.context = context;
        this.messageRepository = context.getBean(MessageRepository.class);
    }

    public void sendResponseToDatabase(String content, String sessionId, Sender sender) {
        Message message = new Message(content, sessionId, sender);
        messageRepository.save(message);
    }
}
