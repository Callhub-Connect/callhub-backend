package callhub.connect.use_case.message;

import callhub.connect.entities.Sender;

public class MessageInputData {
    private final String message;
    private final String sessionId;

    private final Sender sender;

    /**
     * Constructs a MessageInputData object with the provided session ID, message content, and sender information.
     *
     * @param sessionId The unique identifier (ID) of the session associated with the message.
     * @param message   The content of the message.
     * @param sender    The sender information associated with the message.
     */
    public MessageInputData(String sessionId, String message, Sender sender) {
        this.sessionId = sessionId;
        this.message = message;
        this.sender = sender;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content as a string.
     */
    public String getMessage() {
        return this.message;
    }


    /**
     * Gets the session ID of the message.
     *
     * @return The session ID as a string.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Gets the sender information associated with the message.
     *
     * @return The sender information as a Sender object.
     */
    public Sender getSender() {
        return sender;
    }

}
