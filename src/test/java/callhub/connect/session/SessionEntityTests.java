package callhub.connect.session;

import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import callhub.connect.entities.Session;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionEntityTests {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        boolean active = true;
        String code = "testCode";
        Session session = new Session(active, code);

        // Assert
        assertEquals(code, session.getCode());
        assertEquals(new ArrayList<>(), session.getMessages());
        assertEquals(new ArrayList<>(), session.getDocuments());
    }

    @Test
    void testAddMessage() {
        // Arrange
        Session session = new Session(true, "testCode");
        Message message = new Message("content", "id1234", Sender.CUSTOMER);

        // Act
        session.addMessage(message);

        // Assert
        assertEquals(1, session.getMessages().size());
        assertEquals(message, session.getMessages().get(0));
    }

    @Test
    void testAddDocument() {
        // Arrange
        Session session = new Session(true, "testCode");
        String fileID = "testFileID";

        // Act
        session.addDocument(fileID);

        // Assert
        assertEquals(1, session.getDocuments().size());
        assertEquals(fileID, session.getDocuments().get(0));
    }

    @Test
    void testSetInactive() {
        // Arrange
        Session session = new Session(true, "testCode");

        // Act
        session.setInactive();

        // Assert
        assertEquals(false, session.getIsActive());
    }
}

