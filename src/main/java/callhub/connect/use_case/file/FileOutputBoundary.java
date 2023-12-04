package callhub.connect.use_case.file;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface FileOutputBoundary {
    ResponseEntity<Object> getResponse();
}
