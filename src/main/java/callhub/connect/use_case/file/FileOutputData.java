package callhub.connect.use_case.file;

import callhub.connect.entities.FileDocument;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileOutputData implements FileOutputBoundary {
    private Optional<FileDocument> result;
    private ResponseEntity<Object> response;
    private String resultMessage;

//    public FileOutputData() {
//        this.result = result;
//    }

    @Override
    public ResponseEntity<Object> getResponse() {
        return this.response;
//        if (result.isEmpty()) {
//            return new ResponseEntity<>("Something went wrong.", headers, HttpStatus.BAD_REQUEST);
//        } else {
//            Binary data = result.get().getContent();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            byte[] encodedData = data.getData();
//            return new ResponseEntity<>(encodedData, headers, HttpStatus.OK);
//        }
    }

//    @Override
//    public ResponseEntity<String> formatResponse() {
//        if (result.isEmpty()) {
//            return new ResponseEntity<>("Something went wrong.", headers, HttpStatus.BAD_REQUEST);
//        } else {
//            return new ResponseEntity<>("Document " + result.get().getId() + " was uploaded successfully.", headers, HttpStatus.OK);
//        }
//    }

    public void setResult(ResponseEntity<Object> response) {
        this.response = response;
    }
}
