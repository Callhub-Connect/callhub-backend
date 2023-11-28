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

    public Message(String content, LocalDateTime timeStamp, String sessionId, Sender sender) {
        this.content = content;
        this.timeStamp = timeStamp;
        this.sessionId = sessionId;
        this.sender = sender;
    }

    public Message(String content, String sessionId, Sender sender) {
        this.content = content;
        this.timeStamp = LocalDateTime.now();
        this.sessionId = sessionId;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

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
