package callhub.connect.data_access;

import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Session;
import callhub.connect.use_case.file.FileDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileDataAcessObject implements FileDataAccessInterface {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Searches for and returns a FileDocument by its ID.
     *
     * @param id The ID of the FileDocument to be retrieved.
     * @return An Optional containing the FileDocument if found, or an empty Optional if not found.
     */
    @Override
    public Optional<FileDocument> findDocumentByID(String id) {
        return documentRepository.findById(id);
    }

    /**
     * Attempts to save a FileDocument to the document repository.
     *
     * This method tries to save the provided FileDocument object to the repository.
     * If successful, it returns an Optional containing the saved FileDocument.
     * If any exception occurs during the save process, it returns an empty Optional.
     *
     * @param file The FileDocument to be saved.
     * @return An Optional containing the saved FileDocument, or an empty Optional in case of failure.
     */
    @Override
    public Optional<FileDocument> uploadFile(FileDocument file) {
        try {
            return Optional.of(documentRepository.save(file));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Checks if an active session exists with the given code.
     *
     * This method queries the session repository to determine whether there is an active session
     * matching the provided code.
     *
     * @param code The code of the session to be checked.
     * @return true if an active session with the given code exists, false otherwise.
     */
    @Override
    public boolean findSession(String code) {
        return sessionRepository.existsByCodeAndActive(code, true);
    }

    /**
     * Adds a document ID to an active session specified by its code.
     *
     * Retrieves the active session with the specified code and adds the provided document ID to it.
     * The updated session is then saved back to the repository.
     *
     * @param code The code of the active session to which the document ID will be added.
     * @param id The document ID to add to the session.
     */
    @Override
    public void addIDToSession(String code, String id) {
        Session currentSession = sessionRepository.getSessionsByActiveAndCode(true, code);
        currentSession.addDocument(id);
        sessionRepository.save(currentSession);
    }
}
