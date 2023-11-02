package callhub.connect.controllers;
import callhub.connect.data_access.DocumentRepository;
import callhub.connect.data_access.LocalDataAccess;
import callhub.connect.entities.FileDocument;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    DocumentRepository documentRepository;
    @GetMapping("/upload")
    public String uploadPDF () {
        String currentDirectory = System.getProperty("user.dir");
        String pathName = currentDirectory + "/src/main/java/callhub/connect/pdfs/fall2023.pdf";
        FileDocument result;
        try {
            byte[] pdfEncoded = LocalDataAccess.convertPDFToByteArray(pathName);
            Binary data = LocalDataAccess.byteArrayToBinary(pdfEncoded);
            result = documentRepository.save(new FileDocument("timetable", data, LocalDate.now()));
            return "Uploaded! " + result.id;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
