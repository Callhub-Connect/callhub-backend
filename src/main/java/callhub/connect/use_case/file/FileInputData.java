package callhub.connect.use_case.file;

import callhub.connect.entities.FileDocument;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public class FileInputData {
    private FileDocument fileData;
    private MultipartFile file;
    private String id;
    private String name;
    private Binary data;
    private String code;

    public FileInputData(MultipartFile file, String name) {
        this.file = file;
        this.name = name;

        setFileData();
    }

    public FileInputData(String id, MultipartFile file) {
        this.file = file;
        this.id = id;

        setFileData();
    }

    public FileInputData(String id) {
        this.id = id;
    }

    public FileInputData(MultipartFile file, String name, String code) {
        this.file = file;
        this.name = name;
        this.code = code;

        setFileData();
    }

    private byte[] convertPDFToByteArrayUsingFile() throws IOException {
        return file.getBytes();
    }

    private void setFileData() {
        try {
            this.data = this.serializePDF();
            this.fileData = new FileDocument(this.name, this.data, LocalDate.now());
        } catch (IOException e) {
            this.fileData = null;
        }
    }

    public Binary getData() {
        return this.data;
    }

    private Binary serializePDF() throws IOException {
        return byteArrayToBinary(convertPDFToByteArrayUsingFile());
    }

    private Binary byteArrayToBinary(byte[] byteArray) {
        return new Binary(byteArray);
    }

    public String getID() {
        return this.id;
    }

    public FileDocument getFileData() {
        return fileData;
    }

    public String getCode() {
        return this.code;
    }
}
