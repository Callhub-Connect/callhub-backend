package callhub.connect.use_case.file;

import callhub.connect.entities.FileDocument;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FileInteractor implements FileInputBoundary {

    @Autowired
    FileDataAccessInterface fileDataAccessObject;
    @Autowired
    FileOutputBoundary filePresenter;

    HttpHeaders headers = new HttpHeaders();

    public FileInteractor(FileDataAccessInterface fileDataAccessObject, FileOutputBoundary filePresenter) {
        this.fileDataAccessObject = fileDataAccessObject;
        this.filePresenter = filePresenter;
    }


    /**
     * Uploads a file and returns the result as a ResponseEntity.
     *
     * @param fileInputData The file data to be uploaded.
     * @return ResponseEntity containing the file ID and HTTP status OK upon successful upload,
     *         or an error message and HTTP status BAD_REQUEST if the upload fails.
     */
    @Override
    public ResponseEntity<Object> uploadFile(FileInputData fileInputData) {
        Optional<FileDocument> result = fileDataAccessObject.uploadFile(fileInputData.getFileData());
        headers.setContentType(MediaType.TEXT_PLAIN);
        FileOutputData outputData = new FileOutputData();
        if (result.isEmpty()) {
            outputData.setResult(new ResponseEntity<>("Something went wrong.", headers, HttpStatus.BAD_REQUEST));
        } else {
            outputData.setResult(new ResponseEntity<>(result.get().getId(), headers, HttpStatus.OK));
        }
        return filePresenter.sendResponse(outputData);
    }

    /**
     * Retrieves a document by its ID and returns the document data as a ResponseEntity.
     *
     * This method searches for a FileDocument using the provided ID. If the document is found,
     * it returns the document's content as a byte array within a ResponseEntity. If the document
     * is not found, it returns a ResponseEntity with an error message.
     *
     * @param inputData The input data containing the ID of the document to be retrieved.
     * @return ResponseEntity containing the document data and HTTP status OK if the document is found,
     *         or an error message and HTTP status BAD_REQUEST if the document is not found.
     */
    @Override
    public ResponseEntity<Object> findDocumentByID(FileInputData inputData) {
        Optional<FileDocument> result = fileDataAccessObject.findDocumentByID(inputData.getID());
        FileOutputData outputData = new FileOutputData();
        if (result.isEmpty()) {
            outputData.setResult(new ResponseEntity<>("File could not be found.", headers, HttpStatus.BAD_REQUEST));
        } else {
            Binary data = result.get().getContent();
            headers.setContentType(MediaType.APPLICATION_PDF);
            byte[] encodedData = data.getData();
            outputData.setResult(new ResponseEntity<>(encodedData, headers, HttpStatus.OK));
        }
        return filePresenter.sendResponse(outputData);
    }

    /**
     * Adds a PDF file to a specified session and returns the result as a ResponseEntity.
     *
     * This method checks if a session exists for the given code in the inputData. If the session exists,
     * it attempts to upload the PDF file contained in the inputData. Upon successful upload,
     * the file's ID is added to the session, and the ID is returned within a ResponseEntity with HTTP status OK.
     * If the session does not exist or the file upload fails, it returns an error message with an appropriate HTTP status.
     *
     * @param inputData The input data containing the session code and file data to be added.
     * @return ResponseEntity containing the file ID and HTTP status OK if the file is successfully added to the session,
     *         or an error message and appropriate HTTP status (NOT_FOUND or BAD_REQUEST) in case of failure.
     */
    @Override
    public ResponseEntity<Object> addPDFToSession(FileInputData inputData) {
        boolean codeExists = fileDataAccessObject.findSession(inputData.getCode());
        headers.setContentType(MediaType.TEXT_PLAIN);
        FileOutputData outputData = new FileOutputData();
        if (!codeExists) {
            outputData.setResult(new ResponseEntity<>("Session not found.", headers, HttpStatus.NOT_FOUND));
        } else {
            Optional<FileDocument> result = fileDataAccessObject.uploadFile(inputData.getFileData());
            if (result.isEmpty()) {
                outputData.setResult(new ResponseEntity<>("File couldn't be uploaded.", headers, HttpStatus.BAD_REQUEST));
            } else {
                fileDataAccessObject.addIDToSession(inputData.getCode(), result.get().getId());
                outputData.setResult(new ResponseEntity<>(result.get().getId(), headers, HttpStatus.OK));
            }
        }
        return filePresenter.sendResponse(outputData);
    }

    /**
     * Updates an existing file in the database and returns the result as a ResponseEntity.
     *
     * This method searches for a file using the ID provided in fileInputData. If the file is found,
     * it updates the file's upload date and content with the new data from fileInputData.
     * A confirmation message with the file ID is returned within a ResponseEntity with HTTP status OK.
     * If the file is not found, it returns a 'File not found' error message with HTTP status NOT_FOUND.
     *
     * @param fileInputData The input data containing the ID of the file to be updated and the new file content.
     * @return ResponseEntity containing a success message and HTTP status OK if the file is updated,
     *         or an error message and HTTP status NOT_FOUND if the file is not found.
     */
    @Override
    public ResponseEntity<Object> updateFile(FileInputData fileInputData) {
        Optional<FileDocument> existingFileDocumentOpt = fileDataAccessObject.findDocumentByID(fileInputData.getID());
        headers.setContentType(MediaType.TEXT_PLAIN);
        FileOutputData fileOutputData = new FileOutputData();
        if (existingFileDocumentOpt.isEmpty()) {
            fileOutputData.setResult(new ResponseEntity<>("File not found.", headers, HttpStatus.NOT_FOUND));
        } else {
            FileDocument existingFileDocument = existingFileDocumentOpt.get();
            existingFileDocument.setUploadDate(LocalDate.now()); // Update upload date if needed
            existingFileDocument.setContent(fileInputData.getData());
            fileDataAccessObject.uploadFile(existingFileDocument);
            fileOutputData.setResult(new ResponseEntity<>(existingFileDocument.getId() + " was updated successfully.", headers, HttpStatus.OK));
        }
        return filePresenter.sendResponse(fileOutputData);
    }

    /**
     * Retrieves the name of a file based on its unique identifier.
     *
     * This method searches for a file in the database using its ID provided in {@link FileInputData}.
     * If the file is found, the method returns the file's name along with an HTTP status of OK (HttpStatus.OK).
     * If the file is not found, a message indicating the file could not be found is returned along with a BAD_REQUEST (HttpStatus.BAD_REQUEST) status.
     *
     * @param inputData containing the ID of the file to be retrieved.
     * @return A {@link ResponseEntity<Object>} that contains the file name if found, or an error message if not found.
     * The ResponseEntity also includes appropriate HTTP headers and status codes.
     */
    @Override
    public ResponseEntity<Object> getNameFromID(FileInputData inputData) {
        Optional<FileDocument> result = fileDataAccessObject.findDocumentByID(inputData.getID());
        FileOutputData outputData = new FileOutputData();
        if (result.isEmpty()) {
            outputData.setResult(new ResponseEntity<>("File could not be found.", headers, HttpStatus.BAD_REQUEST));
        } else {
            outputData.setResult(new ResponseEntity<>(result.get().getName(), headers, HttpStatus.OK));
        }
        return filePresenter.sendResponse(outputData);
    }
}
