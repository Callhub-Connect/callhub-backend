package callhub.connect.interface_adapter.file_adapter;
import callhub.connect.use_case.file.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileInputBoundary fileInteractor;

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
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
        FileInputData inputData = new FileInputData(file, name);
        return fileInteractor.uploadFile(inputData);
    }

    /**
     * Retrieves a document from the document repository by its unique identifier (ID) and returns it as a PDF file.
     *
     * @param id The unique identifier (ID) of the document to be retrieved.
     * @return ResponseEntity containing the retrieved PDF file if found, or an error message with an appropriate
     * HTTP status code if the document is not found or if an error occurs.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findDocumentByID(@PathVariable String id) throws IOException {
        FileInputData inputData = new FileInputData(id);
        return fileInteractor.findDocumentByID(inputData);
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
        FileInputData inputData = new FileInputData(file, name, code);
        return fileInteractor.addPDFToSession(inputData);
    }

    /**
     * Updates an existing file document with new content provided as a MultipartFile.
     *
     * @param id       The unique identifier (ID) of the file document to be updated.
     * @param file  The MultipartFile representing the new content to replace the existing file's content.
     * @return ResponseEntity indicating the result of the update operation, including success or appropriate error messages and HTTP status codes.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateFile(@PathVariable String id, @RequestParam("file") MultipartFile file) {
            FileInputData inputData = new FileInputData(id, file);
            return fileInteractor.updateFile(inputData);
    }

    @GetMapping("/name/{id}")
    public ResponseEntity<Object> getNameFromID(@PathVariable String id) throws IOException {
        FileInputData inputData = new FileInputData(id);
        return fileInteractor.getNameFromID(inputData);
    }

}
