package callhub.connect.use_case.message;

import callhub.connect.entities.Sender;

import java.util.HashMap;

public interface MessageDataAccessInterface {
    public HashMap<String, String> generateResponse(String message);

    /**
     * Saves message to database, and adds message to its corresponding session
     * @param content message content
     * @param sessionId id for the message's session
     * @param sender CUSTOMER or EMPLOYEE
     */
    public void sendResponseToDatabase(String content, String sessionId, Sender sender);
}
