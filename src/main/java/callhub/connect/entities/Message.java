package callhub.connect.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

    private String getTimeStampString() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss a");
        return df.format(this.timeStamp);
    }

    public String formattedMessage() {
        return String.format("%s %s", this.getTimeStampString(), this.content);
    }
}
