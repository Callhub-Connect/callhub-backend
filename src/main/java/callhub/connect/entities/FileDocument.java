package callhub.connect.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import org.bson.types.Binary;

@Document("documents")
public class FileDocument {
    @Id
    public String id;

    public String name;
    public Binary content;
    public LocalDate timeStamp;

    public FileDocument(String name, Binary content, LocalDate date) {
        this.content = content;
        this.name = name;
        this.timeStamp = date;
    }


}
