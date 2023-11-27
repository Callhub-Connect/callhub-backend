package callhub.connect.data_access;

import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import callhub.connect.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

public class MessageDataAccess {

    private ApplicationContext context;
    private MessageRepository messageRepository;
    private SessionRepository sessionRepository;

    public MessageDataAccess(ApplicationContext context) {
        this.context = context;
        this.messageRepository = context.getBean(MessageRepository.class);
        this.sessionRepository = context.getBean(SessionRepository.class);
    }

    public void sendResponseToDatabase(String content, String sessionId, Sender sender) {
        Message message = new Message(content, sessionId, sender);
        Message result = messageRepository.save(message);
        // add message to session
        try{
            Session currentSession = sessionRepository.findById(sessionId).orElseThrow();
            currentSession.addMessage(result);
            sessionRepository.save(currentSession);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
