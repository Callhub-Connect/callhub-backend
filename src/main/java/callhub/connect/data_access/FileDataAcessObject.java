package callhub.connect.data_access;

import callhub.connect.entities.exceptions.FileLimitExceededException;
import callhub.connect.use_case.file.FileDataAccessInterface;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileDataAcessObject implements FileDataAccessInterface {
    MultipartFile file;

    public FileDataAcessObject(MultipartFile file) {
        this.file = file;
    }

    private byte[] convertPDFToByteArrayUsingFile() throws IOException {
        return file.getBytes();
    }

    @Override
    public Binary serializePDF() throws IOException {
        return byteArrayToBinary(convertPDFToByteArrayUsingFile());
    }

    @Override
    public Binary byteArrayToBinary(byte[] byteArray) {
        return new Binary(byteArray);
    }
}
