package callhub.connect.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.ArrayList;

@Document("sessions")
public class Session {
    @Id
    private String id;

    private String code;
    private java.util.Date startTime;

    private boolean active;
    private ArrayList<Message> messages;
    // private ArrayList<> documents;

    public Session(boolean active, String code){
        this.active = active;
        this.messages = new ArrayList<Message>();
        this.startTime = new java.util.Date(System.currentTimeMillis());
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

}
