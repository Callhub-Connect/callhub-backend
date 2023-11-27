package callhub.connect.data_access;
import callhub.connect.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
public interface MessageRepository extends MongoRepository<Message, String>{

    @Query("{userId: '?0'}")
    public List<Message> findByUserId(String userId);

}
