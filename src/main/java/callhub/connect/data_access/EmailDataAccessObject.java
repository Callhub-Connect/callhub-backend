package callhub.connect.data_access;

import callhub.connect.entities.Message;
import callhub.connect.entities.Session;
import callhub.connect.entities.exceptions.NoMessagesException;
import callhub.connect.entities.exceptions.SessionNotFoundException;
import callhub.connect.use_case.email.EmailDataAccessInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailDataAccessObject implements EmailDataAccessInterface {
    private final SessionRepository sessionRepository;

    public EmailDataAccessObject(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Retrieves the transcript of messages for a given session code.
     *
     * @param id The session code to identify the session.
     * @return A ResponseEntity containing the transcript of messages as a String.
     * @throws NullPointerException if there is no session with the id in the sessionRepository.
     */
    @Override
    public String getTranscript(String id) {
        boolean sessionExists = sessionRepository.existsById(id);
        if (!sessionExists) {
            throw new SessionNotFoundException("Session not found.");
        }

        Session session = sessionRepository.getSessionById(id);
        ArrayList<Message> messagesList = session.getMessages();

        if (!(messagesList.isEmpty())) {
            StringBuilder transcript = new StringBuilder();
            for (Message message : messagesList) {
                transcript.append(message.formattedMessage()).append("\n");
            }
            return transcript.toString();
        }
        else{
            throw new NoMessagesException("No messages found.");
        }
    }

    /**
     * Retrieves the date messages were sent for a given session code, assuming all messages
     * were sent on the same day as the first message.
     *
     * @param id The session code to identify the session.
     * @return A ResponseEntity containing the date of messages as a String.
     * @throws NullPointerException if there is no session with the id in the sessionRepository.
     */
    @Override
    public String getDate(String id) {
        boolean sessionExists = sessionRepository.existsById(id);
        if (!sessionExists) {
            throw new SessionNotFoundException("Session not found.");
        }

        Session session = sessionRepository.getSessionById(id);
        ArrayList<Message> messagesList = session.getMessages();

        if (!(messagesList.isEmpty())) {
            Message firstMessage = messagesList.get(0);
            return firstMessage.getDateString();
        }
        else {
            throw new NoMessagesException("No messages found.");
        }
    }
}
