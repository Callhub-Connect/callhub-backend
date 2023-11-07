package callhub.connect.data_access;
import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentRepository extends MongoRepository<FileDocument, String>{
    // implement custom query
}
