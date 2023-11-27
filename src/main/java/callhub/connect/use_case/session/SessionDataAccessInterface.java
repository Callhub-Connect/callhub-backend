package callhub.connect.use_case.session;

import callhub.connect.entities.Message;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface SessionDataAccessInterface {
    public HashMap<String, String> generateNewSession();
    public HashMap<String, String> joinSession(String code);

    public void addMessageToSession(String sessionId, Message message);
}
