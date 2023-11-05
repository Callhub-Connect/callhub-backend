package callhub.connect.controllers;
import callhub.connect.data_access.DataAccess;
import callhub.connect.data_access.DocumentRepository;
import callhub.connect.data_access.LocalDataAccess;
import callhub.connect.data_access.NetworkDataAccess;
import callhub.connect.entities.FileDocument;
import callhub.connect.entities.exceptions.FileLimitExceededException;
import org.springframework.http.MediaType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    public DocumentRepository documentRepository;
    HttpHeaders headers = new HttpHeaders();
    DataAccess dataAccessObject;
    @GetMapping("/upload_local")
    public ResponseEntity<String> uploadPDFLocal () {
        String currentDirectory = System.getProperty("user.dir");
        String pathName = currentDirectory + "/src/main/java/callhub/connect/pdfs/fall2023.pdf";
        FileDocument result;

        dataAccessObject = new LocalDataAccess(pathName);
        Binary data;
        try {
            data = dataAccessObject.serializePDF();
        } catch (FileLimitExceededException e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
        result = documentRepository.save(new FileDocument("timetable", data, LocalDate.now()));
        return new ResponseEntity<>("Uploaded! " + result.getId(), headers, HttpStatus.OK);
    }

    @PostMapping("/upload_network")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        dataAccessObject = new NetworkDataAccess(file);
        FileDocument result;
        Binary data;
        try {
            data = dataAccessObject.serializePDF();
            result = documentRepository.save(new FileDocument("timetable", data, LocalDate.now()));
            return new ResponseEntity<>(result.getId(), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public Object findDocumentByID (@PathVariable String id) {
        Optional<FileDocument> item = documentRepository.findById(id);
        if (item.isEmpty()) {
            return "item could not be found.";
        } else {
            // decode Binary to byte[] and return as a pdf
            Binary data = item.get().getContent();
            headers.setContentType(MediaType.APPLICATION_PDF);
            byte[] encodedData = data.getData();
            return new ResponseEntity<>(encodedData, headers, HttpStatus.OK);
        }
    }
}
