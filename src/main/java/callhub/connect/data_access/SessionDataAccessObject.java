package callhub.connect.data_access;

import callhub.connect.entities.Message;
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

    /**
     * Creates a new active session and returns its details.
     *
     * This method generates a new session with a unique code, saves it to the repository,
     * and then returns a HashMap containing the session's code and ID.
     *
     * @return A HashMap with keys "sessionCode" and "sessionId", holding the new session's code and ID.
     */
    public HashMap<String, String> generateNewSession() {
        Session session = sessionRepository.save(new Session(true, generateSessionCode()));
        // Create response
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", session.getCode());
        responseBody.put("sessionId", session.getId());
        return responseBody;
    }

    /**
     * Retrieves an active session by its code and returns its details.
     *
     * This method looks up an active session using the provided code,
     * and then returns a HashMap containing the session's code and ID.
     *
     * @param code The code of the active session to join.
     * @return A HashMap with keys "sessionCode" and "sessionId", holding the session's code and ID.
     */
    public HashMap<String, String> joinSession(String code) {
        Session session = sessionRepository.getSessionsByActiveAndCode(true, code);
        // Create response
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("sessionCode", session.getCode());
        responseBody.put("sessionId", session.getId());

        return responseBody;
    }

    /**
     * Ends an active session identified by the given code and returns an empty response body.
     *
     * This method retrieves an active session using the provided code, sets it to inactive,
     * and saves the updated session state to the repository. It returns an empty HashMap as the response.
     *
     * @param code The code of the active session to be ended.
     * @return An empty HashMap, indicating the session has been successfully ended.
     */
    @Override
    public HashMap<String, String> endSession(String code) {
        Session session = sessionRepository.getSessionsByActiveAndCode(true, code);
        // end session
        session.setInactive();
        sessionRepository.save(session);
        HashMap<String, String> responseBody = new HashMap<>();
        return responseBody;
    }

    /**
     * Adds message to the session with sessionId in the database.
     *
     * @param sessionId session id
     * @param message message to be added
     */
    public void addMessageToSession(String sessionId, Message message){
        Session currentSession = sessionRepository.findById(sessionId).orElseThrow();
        currentSession.addMessage(message);
        sessionRepository.save(currentSession);
    }

    /**
     * Generates a unique session code.
     *
     * This method creates a random alphanumeric string of a predefined length as the session code.
     * It ensures that the generated code is unique by checking the existing active sessions in the repository.
     * The process repeats until a unique code is generated.
     *
     * @return A unique session code as a String.
     */
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
