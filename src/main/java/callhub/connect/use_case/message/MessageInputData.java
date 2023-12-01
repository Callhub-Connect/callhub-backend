package callhub.connect.use_case.message;

import callhub.connect.entities.Sender;

public class MessageInputData {
    private final String message;
    private final String sessionId;

    private final Sender sender;

    public MessageInputData(String sessionId, String message, Sender sender) {
        this.sessionId = sessionId;
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return this.message;
    }


    public String getSessionId() {
        return sessionId;
    }

    public Sender getSender() {
        return sender;
    }

}
