package callhub.connect.use_case.email;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmailInputBoundary {
    ResponseEntity<String> getTranscript(EmailInputData inputData);

    ResponseEntity<String> getDate(EmailInputData inputData);
}
