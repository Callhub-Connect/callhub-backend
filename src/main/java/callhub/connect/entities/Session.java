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
    private ArrayList<String> documents;


    /**
     * Constructs a Session object with the provided attributes.
     *
     * @param active    Indicates whether the session is active.
     * @param code      The unique identifier (code) associated with the session.
     */
    public Session(boolean active, String code){
        this.active = active;
        this.messages = new ArrayList<Message>();
        this.startTime = new java.util.Date(System.currentTimeMillis());
        this.code = code;
        this.documents = new ArrayList<String>();
    }

    /**
     * Gets the unique identifier (ID) associated with the session.
     *
     * @return The ID of the session as a string.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the code associated with the session.
     *
     * @return The code of the session as a string.
     */
    public String getCode() {
        return code;
    }

    /**
     * Adds a document (identified by its file ID) to the session's list of associated documents.
     *
     * @param fileID The unique identifier (ID) of the document to be added to the session.
     */
    public void addDocument(String fileID) {
        documents.add(fileID);
    }

    /**
     * Gets the list of messages associated with the session.
     *
     * @return An ArrayList containing the messages associated with the session.
     */
    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    /**
     * Adds a message to the list of messages associated with the session.
     *
     * @param message The message to be added to the session's list of messages.
     */
    public void addMessage(Message message){
        messages.add(message);
    }

    /**
     * Sets the session status to inactive.
     */
    public void setInactive(){
        this.active = false;
    }

}
