package callhub.connect.use_case.email;

import org.springframework.http.ResponseEntity;

public interface EmailInputBoundary {
    ResponseEntity<String> getTranscript(EmailInputData inputData);

    ResponseEntity<String> getDate(EmailInputData inputData);
}
