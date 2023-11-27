package callhub.connect.data_access;

import callhub.connect.entities.Session;
import callhub.connect.use_case.session.SessionDataAccessInterface;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;

@Service
public class SessionDataAccessObject implements SessionDataAccessInterface {
    private final SessionRepository sessionRepository;
    private final static int CODE_LENGTH = 6;

    public SessionDataAccessObject(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public HashMap<String, String> generateNewSession() {
        Session session = sessionRepository.save(new Session(true, generateSessionCode()));
        // Create response
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", session.getCode());
        responseBody.put("sessionId", session.getId());
        return responseBody;
    }

    public HashMap<String, String> joinSession(String code) {
        Session session = sessionRepository.getSessionsByActiveAndCode(true, code);
        // Create response
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", session.getCode());
        responseBody.put("sessionId", session.getId());

        return responseBody;
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
