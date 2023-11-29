package callhub.connect.use_case.message;

public class MessageInputData {
    private final String message;
    private final String sessionId;

    public MessageInputData(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
