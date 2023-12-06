package callhub.connect.use_case.file;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FileInputBoundary {
    ResponseEntity<Object> uploadFile(FileInputData fileInputData) throws IOException;
    ResponseEntity<Object> findDocumentByID(FileInputData fileInputData) throws IOException;
    ResponseEntity<Object> addPDFToSession(FileInputData fileInputData);
    ResponseEntity<Object> updateFile(FileInputData fileInputData);
    ResponseEntity<Object> getNameFromID(FileInputData inputData);
}
