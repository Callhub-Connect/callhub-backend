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
    private final Binary content;
    public LocalDate timeStamp;

    public FileDocument(String name, Binary content, LocalDate timeStamp) {
        this.content = content;
        this.name = name;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) { this.id = id; }

    public Binary getContent() {
        return this.content;
    }
}
