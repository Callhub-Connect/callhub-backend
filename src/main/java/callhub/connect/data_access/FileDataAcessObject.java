package callhub.connect.data_access;

import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Session;
import callhub.connect.use_case.file.FileDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileDataAcessObject implements FileDataAccessInterface {
//    MultipartFile file;
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SessionRepository sessionRepository;

//    public FileDataAcessObject(MultipartFile file) {
//        this.file = file;
//    }

//    private byte[] convertPDFToByteArrayUsingFile(MultipartFile file) throws IOException {
//        return file.getBytes();
//    }
//
//    public Binary serializePDF(MultipartFile file) throws IOException {
//        return byteArrayToBinary(convertPDFToByteArrayUsingFile(file));
//    }
//
//    public Binary byteArrayToBinary(byte[] byteArray) {
//        return new Binary(byteArray);
//    }

    @Override
    public Optional<FileDocument> findDocumentByID(String id) {
        return documentRepository.findById(id);
    }

    @Override
    public Optional<FileDocument> uploadFile(FileDocument file) {
        try {
            return Optional.of(documentRepository.save(file));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean findSession(String code) {
        return sessionRepository.existsByCodeAndActive(code, true);
    }

    @Override
    public void addIDToSession(String code, String id) {
        Session currentSession = sessionRepository.getSessionsByActiveAndCode(true, code);
        currentSession.addDocument(id);
        sessionRepository.save(currentSession);
    }
}
