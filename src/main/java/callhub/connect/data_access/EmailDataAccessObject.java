package callhub.connect.data_access;

import callhub.connect.entities.Message;
import callhub.connect.entities.Session;
import callhub.connect.use_case.email.EmailDataAccessInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailDataAccessObject implements EmailDataAccessInterface {
    private final SessionRepository sessionRepository;

    public EmailDataAccessObject(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public String getTranscript(String id) {
        boolean sessionExists = sessionRepository.existsById(id);
        if (!sessionExists) {
            return "This session is inactive or does not exist.";
        }

        Session session = sessionRepository.getSessionById(id);
        ArrayList<Message> messagesList = session.getMessages();

        StringBuilder transcript = new StringBuilder();
        for (Message message : messagesList) {
            transcript.append(message.formattedMessage()).append("\n");
        }
        return transcript.toString();

    }

    @Override
    public String getDate(String id) {
        boolean sessionExists = sessionRepository.existsById(id);
        if (!sessionExists) {
            return "This session is inactive or does not exist.";
        }

        try {
            Session session = sessionRepository.getSessionById(id);
            ArrayList<Message> messagesList = session.getMessages();

            Message firstMessage = messagesList.get(0);
            return firstMessage.getDateString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
