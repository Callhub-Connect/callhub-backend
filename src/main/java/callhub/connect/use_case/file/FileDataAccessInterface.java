package callhub.connect.use_case.file;

import org.bson.types.Binary;

import java.io.IOException;

public interface FileDataAccessInterface {
    Binary serializePDF() throws IOException;
    Binary byteArrayToBinary(byte[] byteArray);
}
