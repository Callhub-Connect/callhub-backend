package callhub.connect.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("message")
public class Message {

    @Id
    private String id;
    private String content;
    private LocalDate timeStamp;
    private String userId;

    private Sender sender;

    public Message(String content, LocalDate timeStamp, Sender sender) {
        this.content = content;
        this.timeStamp = timeStamp;
        this.sender = sender;
    }

    public Message(String content, Sender sender) {
        this.content = content;
        this.timeStamp = LocalDate.now();
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
