package callhub.connect.controllers;

import callhub.connect.use_case.session.SessionInputData;
import org.springframework.http.ResponseEntity;

public class MockSessionInteractor {
        public ResponseEntity<String> joinSession(SessionInputData inputData) {
            // Your implementation logic here
            // For example, let's assume it always returns a success response for testing purposes
            return ResponseEntity.ok("Joined session");
        }
}
