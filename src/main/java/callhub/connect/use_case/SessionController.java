package callhub.connect.use_case;

import callhub.connect.data_access.MessageRepository;
import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Session;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/session")
public class SessionController {

    public SessionRepository sessionRepository;

    private final static int CODE_LENGTH = 6;

    public SessionController(SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/new-session")
    public ResponseEntity<String> newSession() {
        HttpHeaders headers = new HttpHeaders();
        Session result = sessionRepository.save(new Session(true, generateSessionCode()));

        // Create response
        Gson gson = new Gson();
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", result.getCode());
        responseBody.put("sessionId", result.getId());
        return new ResponseEntity<>(gson.toJson(responseBody), headers, HttpStatus.OK);
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
