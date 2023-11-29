package callhub.connect.use_case.session;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SessionInputBoundary {
    ResponseEntity<String> newSession();
    ResponseEntity<String> joinSession(SessionInputData inputData);
}
