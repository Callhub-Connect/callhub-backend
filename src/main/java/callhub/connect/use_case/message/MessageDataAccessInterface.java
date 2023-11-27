package callhub.connect.use_case.message;

import callhub.connect.entities.Sender;

import java.util.HashMap;

public interface MessageDataAccessInterface {
    public HashMap<String, String> generateResponse(String message);

    public void sendResponseToDatabase(String content, String sessionId, Sender sender);
}
