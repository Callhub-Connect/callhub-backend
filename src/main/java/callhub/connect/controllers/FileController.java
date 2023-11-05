package callhub.connect.controllers;
import callhub.connect.data_access.DataAccess;
import callhub.connect.data_access.DocumentRepository;
import callhub.connect.data_access.LocalDataAccess;
import callhub.connect.data_access.NetworkDataAccess;
import callhub.connect.entities.FileDocument;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    public DocumentRepository documentRepository;
    DataAccess dataAccessObject;
    @GetMapping("/upload_local")
    public String uploadPDFLocal () {
        String currentDirectory = System.getProperty("user.dir");
        String pathName = currentDirectory + "/src/main/java/callhub/connect/pdfs/fall2023.pdf";
        FileDocument result;

        dataAccessObject = new LocalDataAccess(pathName);
        Binary data;
        try {
            data = dataAccessObject.serializePDF();
        } catch (IOException e) {
            return e.getMessage();
        }
        result = documentRepository.save(new FileDocument("timetable", data, LocalDate.now()));
        return "Uploaded! " + result.id;
    }

    @PostMapping("/upload_network")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        dataAccessObject = new NetworkDataAccess(file);
        FileDocument result;
        Binary data;
        try {
            data = dataAccessObject.serializePDF();
            result = documentRepository.save(new FileDocument("timetable", data, LocalDate.now()));
            return result.id;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
