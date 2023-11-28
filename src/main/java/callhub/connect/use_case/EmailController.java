package callhub.connect.use_case;

import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/transcript")
public class EmailController {

    public SessionRepository sessionRepository;

    public EmailController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    /**
     * Retrieves the transcript of messages for a given session code.
     *
     * @param code The session code to identify the session.
     * @return A ResponseEntity containing the transcript of messages as a String.
     * If the session or messages are not found, an empty transcript is returned.
     */
    @GetMapping("/{code}")
    public ResponseEntity<String> getTranscript(@PathVariable String code) {
        HttpHeaders headers = new HttpHeaders();
        boolean sessionExists = sessionRepository.existsById(code);
        if (!sessionExists) {
            new ResponseEntity<>("This session is inactive or does not exist.", headers, HttpStatus.NOT_FOUND);
        }

        try {
            Session session = sessionRepository.getSessionsByActiveAndCode(true, code);
            ArrayList<Message> messagesList = session.getMessages();

            // assume all messages are sent on the same day
            Message firstMessage = messagesList.get(0);
            StringBuilder transcript = new StringBuilder(firstMessage.getDateString());
            for (Message message : messagesList) {
                transcript.append("\n").append(message.formattedMessage());
            }
            return new ResponseEntity<>(transcript.toString(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}