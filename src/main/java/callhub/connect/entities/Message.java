package callhub.connect.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document("message")
public class Message {

    @Id
    private String id;
    private String content;
    private LocalDateTime timeStamp;
    private String userId;
    private String sessionId;
    private Sender sender;

    public Message(){}

    /**
     * Constructs a Message object with the provided attributes.
     *
     * @param content    The content of the message.
     * @param timeStamp  The timestamp indicating when the message was sent.
     * @param sessionId  The unique identifier (ID) of the session associated with the message.
     * @param sender     The sender information associated with the message.
     */
    public Message(String content, LocalDateTime timeStamp, String sessionId, Sender sender) {
        this.content = content;
        this.timeStamp = timeStamp;
        this.sessionId = sessionId;
        this.sender = sender;
    }

    /**
     * Constructs a Message object with the provided attributes.
     *
     * @param content    The content of the message.
     * @param sessionId  The unique identifier (ID) of the session associated with the message.
     * @param sender     The sender information associated with the message.
     */
    public Message(String content, String sessionId, Sender sender) {
        this.content = content;
        this.timeStamp = LocalDateTime.now();
        this.sessionId = sessionId;
        this.sender = sender;
    }

    /**
     * Gets the content of the message.
     *
     * @return The content of the message as a string.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message to the provided value.
     *
     * @param content The new content to be set for the message.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the date the message was sent as a formatted string.
     *
     * @return A formatted string representing the date of the message in the format "Month dd, yyyy".
     */
    public String getDateString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("LLLL dd, yyyy");
        return this.timeStamp.format(dateFormatter);
    }

    /**
     * Gets a formatted string representation of the message's timestamp in "HH:mm:ss" format.
     *
     * @return The formatted timestamp string.
     */
    private String getTimeStampString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.timeStamp.format(timeFormatter);
    }

    /**
     * Returns a string that formats the message with the time it was sent, its sender and its content.
     * @return a formatted message.
     */
    public String formattedMessage() {
        return String.format("%s %s: %s", this.getTimeStampString(), this.sender.name(), this.content);
    }
}
