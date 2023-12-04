package callhub.connect.use_case.file;

import callhub.connect.data_access.DocumentRepository;
import callhub.connect.entities.FileDocument;
import callhub.connect.use_case.session.SessionOutputBoundary;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

//    public FileInteractor(FileOutputBoundary filePresenter) {
//        this.filePresenter = filePresenter;
//    }

    @Override
    public ResponseEntity<Object> uploadFile(FileInputData fileInputData) {
        Optional<FileDocument> result = fileDataAccessObject.uploadFile(fileInputData.getFileData());
        headers.setContentType(MediaType.TEXT_PLAIN);
        FileOutputData outputData = new FileOutputData();
        if (result.isEmpty()) {
            outputData.setResult(new ResponseEntity<>("Something went wrong.", headers, HttpStatus.BAD_REQUEST));
        } else {
            outputData.setResult(new ResponseEntity<>("Document " + result.get().getId() + " was uploaded successfully.", headers, HttpStatus.OK));
        }
        return outputData.getResponse();
    }

    @Override
    public ResponseEntity<Object> findDocumentByID(FileInputData inputData) {
        Optional<FileDocument> result = fileDataAccessObject.findDocumentByID(inputData.getID());
        FileOutputData outputData = new FileOutputData();
        if (result.isEmpty()) {
            outputData.setResult(new ResponseEntity<>("Something went wrong.", headers, HttpStatus.BAD_REQUEST));
        } else {
            Binary data = result.get().getContent();
            headers.setContentType(MediaType.APPLICATION_PDF);
            byte[] encodedData = data.getData();
            outputData.setResult(new ResponseEntity<>(encodedData, headers, HttpStatus.OK));
        }
        return outputData.getResponse();
    }

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
                outputData.setResult(new ResponseEntity<>(inputData.getCode() + " was updated with document " + result.get().getId(), headers, HttpStatus.OK));
            }
        }
        return outputData.getResponse();
    }

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
        return fileOutputData.getResponse();
    }
}
