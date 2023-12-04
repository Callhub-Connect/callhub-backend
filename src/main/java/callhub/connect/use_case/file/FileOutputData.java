package callhub.connect.use_case.file;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileOutputData {
    private ResponseEntity<Object> response;

    public ResponseEntity<Object> getResponse() {
        return this.response;
    }

    public void setResult(ResponseEntity<Object> response) {
        this.response = response;
    }
}
