package callhub.connect.use_case;

import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Session;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/session")
public class SessionController {

    public SessionRepository sessionRepository;
    private static final Gson gson = new Gson();

    private final static int CODE_LENGTH = 6;

    public SessionController(SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/new-session")
    public ResponseEntity<String> newSession() {
        HttpHeaders headers = new HttpHeaders();
        Session session = sessionRepository.save(new Session(true, generateSessionCode()));
        // Create response
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", session.getCode());
        responseBody.put("sessionId", session.getId());
        return new ResponseEntity<>(gson.toJson(responseBody), headers, HttpStatus.OK);
    }

    @GetMapping("/join/{code}")
    public ResponseEntity<String> joinSession(@PathVariable String code) {
        HttpHeaders headers = new HttpHeaders();
        Session session = sessionRepository.getSessionsByActiveAndCode(true, code);
        // Create response
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", session.getCode());
        responseBody.put("sessionId", session.getId());
        return new ResponseEntity<>(gson.toJson(responseBody), headers, HttpStatus.OK);
    }

    /**
     * Retrieves the transcript of messages for a given session code.
     *
     * @param code The session code to identify the session.
     * @return A ResponseEntity containing the transcript of messages as a String.
     *         If the session or messages are not found, an empty transcript is returned.
     */
    @GetMapping("/transcript/{code}")
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

    private String generateSessionCode(){
        final String ALLOWED_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        do {
            for (int i = 0; i < CODE_LENGTH; i++) {
                code.append(ALLOWED_CHAR.charAt(random.nextInt(ALLOWED_CHAR.length())));
            }

        } while (sessionRepository.existsByCodeAndActive(code.toString(), true)); //ensures code is unique

        return code.toString();
    }


}
