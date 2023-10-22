package callhub.connect.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("message")
public class Message {

    @Id
    public String id;

    public String content;
    public LocalDate timeStamp;
    public String userId;

    public Message(String content, LocalDate timeStamp) {
        this.content = content;
        this.timeStamp = timeStamp;
    }
}
