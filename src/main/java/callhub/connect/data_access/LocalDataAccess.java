package callhub.connect.data_access;
import callhub.connect.entities.exceptions.FileLimitExceededException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalDataAccess extends DataAccess {
    private String pathName;
    public LocalDataAccess(String pathName) {
        this.pathName = pathName;

    }
    private static byte[] convertPDFToByteArrayUsingPath(String filePath) throws IOException {
        File pdfFile = new File(filePath);
        return getPDFBytes(pdfFile);
    }


    @Override
    public Binary serializePDF() throws IOException {
        return byteArrayToBinary(convertPDFToByteArrayUsingPath(this.pathName));
    }
}
