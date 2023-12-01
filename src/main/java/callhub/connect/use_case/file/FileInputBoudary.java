package callhub.connect.use_case.file;

import org.springframework.http.ResponseEntity;

public interface FileInputBoudary {
    ResponseEntity<String> uploadFile();
    ResponseEntity<Object> findDocumentByID();
    ResponseEntity<Object> addPDFToSession();
    ResponseEntity<String> updateFile();

}
