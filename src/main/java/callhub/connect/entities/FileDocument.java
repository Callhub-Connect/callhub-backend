package callhub.connect.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import org.bson.types.Binary;

@Document("documents")
public class FileDocument {
    @Id
    private String id;

    public String name;
    private Binary content = null;
    public LocalDate timeStamp;

    /**
     * Constructs a FileDocument object with the provided attributes.
     *
     * @param name      The name associated with the file document.
     * @param content   The binary content of the file document.
     * @param timeStamp The timestamp indicating when the file document was created.
     */
    public FileDocument(String name, Binary content, LocalDate timeStamp) {
        this.content = content;
        this.name = name;
        this.timeStamp = timeStamp;
    }

    /**
     * Gets the unique identifier (ID) associated with the file document.
     *
     * @return The ID of the file document as a string.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the binary content of the file document.
     *
     * @return The binary content of the file document.
     */
    public Binary getContent() {
        return this.content;
    }

    /**
     * Sets the binary content of the file document.
     *
     * @param newContent The new binary content to be set for the file document.
     */
    public void setContent(Binary newContent) {
        this.content = newContent;
    }

    /**
     * Sets the upload date of the file document to the provided LocalDate.
     *
     * @param now The LocalDate representing the new upload date for the file document.
     */
    public void setUploadDate(LocalDate now) {
        this.timeStamp = now;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
}
