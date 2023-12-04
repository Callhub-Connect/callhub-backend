package callhub.connect.use_case.file;

import callhub.connect.entities.FileDocument;
import org.bson.types.Binary;

import java.io.IOException;
import java.util.Optional;

public interface FileDataAccessInterface {
    Optional<FileDocument> findDocumentByID(String ID);
    Optional<FileDocument> uploadFile(FileDocument file);
    boolean findSession(String code);
    void addIDToSession(String code, String id);
}
