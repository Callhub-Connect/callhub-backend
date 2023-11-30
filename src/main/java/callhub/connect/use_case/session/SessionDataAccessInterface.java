package callhub.connect.use_case.session;

import callhub.connect.entities.Message;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface SessionDataAccessInterface {
    public HashMap<String, String> generateNewSession();
    public HashMap<String, String> joinSession(String code);

    /**
     * Adds message to session with sessionId
     * @param sessionId id for session to add message to
     * @param message message to be added
     */
    public void addMessageToSession(String sessionId, Message message);
}
