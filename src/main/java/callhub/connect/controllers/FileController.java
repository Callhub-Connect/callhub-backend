package callhub.connect.controllers;
import callhub.connect.data_access.*;
import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Session;
import callhub.connect.entities.exceptions.FileLimitExceededException;
import org.springframework.http.MediaType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    public DocumentRepository documentRepository;
    @Autowired
    public SessionRepository sessionRepository;
    HttpHeaders headers = new HttpHeaders();
    DataAccess dataAccessObject;


    /**
     * Uploads a PDF document from the local file system and saves it to the document repository.
     * IMPORTANT: deprecated only use for testing purposes
     *
     * @param name The name to be associated with the uploaded PDF document.
     * @return ResponseEntity containing a message indicating successful upload along with the document's ID if successful,
     * or an error message with an appropriate HTTP status code if an error occurs (e.g., file limit exceeded or bad request).
     */
    @GetMapping("/upload_local")
    public ResponseEntity<String> uploadPDFLocal(@RequestParam("name") String name) {
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
        result = documentRepository.save(new FileDocument(name, data, LocalDate.now()));
        return new ResponseEntity<>("Uploaded! " + result.getId(), headers, HttpStatus.OK);
    }

    /**
     * Deprecated: This method is intended for testing purposes only.
     * Uploads a file from the network using a POST request, serializes it, and saves it to the document repository.
     *
     * @param file The MultipartFile representing the uploaded file from the network.
     * @param name The name to be associated with the uploaded file/document.
     * @return ResponseEntity containing the ID of the saved document if successful, or an error message with an appropriate
     * HTTP status code if an error occurs (e.g., file limit exceeded or bad request).
     */
    @PostMapping("/upload_network")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        dataAccessObject = new NetworkDataAccess(file);
        FileDocument result;
        Binary data;
        try {
            data = dataAccessObject.serializePDF();
            result = documentRepository.save(new FileDocument(name, data, LocalDate.now()));
            return new ResponseEntity<>(result.getId(), headers, HttpStatus.OK);
        } catch (FileLimitExceededException e) {
            return new ResponseEntity<>("File is too large.", headers, HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a document from the document repository by its unique identifier (ID) and returns it as a PDF file.
     *
     * @param id The unique identifier (ID) of the document to be retrieved.
     * @return ResponseEntity containing the retrieved PDF file if found, or an error message with an appropriate
     * HTTP status code if the document is not found or if an error occurs.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findDocumentByID(@PathVariable String id) {
        Optional<FileDocument> item = documentRepository.findById(id);
        if (item.isEmpty()) {
            return new ResponseEntity<>("File could not be found.", headers, HttpStatus.BAD_REQUEST);
        } else {
            // decode Binary to byte[] and return as a pdf
            Binary data = item.get().getContent();
            headers.setContentType(MediaType.APPLICATION_PDF);
            byte[] encodedData = data.getData();
            return new ResponseEntity<>(encodedData, headers, HttpStatus.OK);
        }
    }

    /**
     * Saves a PDF document to the documents collection and associates its ID with a session put under the session's
     * "documents" field.
     *
     * @param file   The MultipartFile representing the uploaded PDF document.
     * @param name   The name to be associated with the uploaded PDF document.
     * @param code   The unique identifier (code) of the session to which the PDF document will be linked.
     * @return ResponseEntity containing the ID of the saved PDF document if successful, or an error message with an appropriate
     * HTTP status code if the session is inactive or does not exist, if an issue occurs during document serialization, or if any other
     * error is encountered.
     */
    @PostMapping("/session_add_pdf")
    public ResponseEntity<Object> addPDFToSession(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("session") String code) {
        boolean sessionExists = sessionRepository.existsById(code);
        if (!sessionExists) {
            new ResponseEntity<>("This session is inactive or does not exist.", headers, HttpStatus.NOT_FOUND);
        }

        dataAccessObject = new NetworkDataAccess(file);
        FileDocument result = new FileDocument();
        Binary data;
        try {
            data = dataAccessObject.serializePDF();
            result = documentRepository.save(new FileDocument(name, data, LocalDate.now()));
        } catch (IOException e) {
            new ResponseEntity<>("An issue occurred.", headers, HttpStatus.BAD_REQUEST);
        }
        try {
            Session currentSession = sessionRepository.getSessionsByActiveAndCode(true, code);
            currentSession.addDocument(result.getId());
            sessionRepository.save(currentSession);
            return new ResponseEntity<>(result.getId(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing file document with new content provided as a MultipartFile.
     *
     * @param id       The unique identifier (ID) of the file document to be updated.
     * @param newFile  The MultipartFile representing the new content to replace the existing file's content.
     * @return ResponseEntity indicating the result of the update operation, including success or appropriate error messages and HTTP status codes.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFile(@PathVariable String id, @RequestParam("file") MultipartFile newFile) {
        Optional<FileDocument> existingFileDocumentOpt = documentRepository.findById(id);

        if (existingFileDocumentOpt.isEmpty()) {
            return new ResponseEntity<>("File not found.", headers, HttpStatus.NOT_FOUND);
        }

        FileDocument existingFileDocument = existingFileDocumentOpt.get();
        dataAccessObject = new NetworkDataAccess(newFile);

        try {
            Binary newContent = dataAccessObject.serializePDF();
            existingFileDocument.setContent(newContent);
            existingFileDocument.setUploadDate(LocalDate.now()); // Update upload date if needed
            documentRepository.save(existingFileDocument);
            return new ResponseEntity<>("File updated successfully!", headers, HttpStatus.OK);
        } catch (FileLimitExceededException e) {
            return new ResponseEntity<>("File is too large.", headers, HttpStatus.PAYLOAD_TOO_LARGE);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}
