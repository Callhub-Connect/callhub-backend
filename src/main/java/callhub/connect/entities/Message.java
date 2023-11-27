package callhub.connect.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Document("message")
public class Message {

    @Id
    private String id;
    private String content;
    private final LocalTime timeStamp;
    private String userId;
    private final String sessionId;
    private final Sender sender;

    public Message(String content, LocalTime timeStamp, String sessionId, Sender sender) {
        this.content = content;
        this.timeStamp = timeStamp;
        this.sessionId = sessionId;
        this.sender = sender;
    }

    public Message(String content, String sessionId, Sender sender) {
        this.content = content;
        this.timeStamp = LocalTime.now();
        this.sessionId = sessionId;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String getTimeStampString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.timeStamp.format(formatter);
    }

    public String formattedMessage() {
        return String.format("%s %s: %s", this.getTimeStampString(), this.sender.name(), this.content);
    }
}
