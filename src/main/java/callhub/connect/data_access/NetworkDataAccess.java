package callhub.connect.data_access;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class NetworkDataAccess extends DataAccess {

    MultipartFile file;

    public NetworkDataAccess(MultipartFile file) {
        this.file = file;
    }

    private byte[] convertPDFToByteArrayUsingFile() throws IOException {
        return file.getBytes();
    }

    @Override
    public Binary serializePDF() throws IOException {
        return byteArrayToBinary(convertPDFToByteArrayUsingFile());
    }
}
