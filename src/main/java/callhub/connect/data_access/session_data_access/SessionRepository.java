package callhub.connect.data_access.session_data_access;

import callhub.connect.entities.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {

    boolean existsByCodeAndActive(String code, boolean active);

    Session getSessionsByActiveAndCode(boolean active, String code);
}
