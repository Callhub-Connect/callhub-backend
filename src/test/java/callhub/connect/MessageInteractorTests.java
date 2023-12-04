package callhub.connect;

import callhub.connect.data_access.MessageRepository;
import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import callhub.connect.entities.Session;
import callhub.connect.use_case.message.MessageInputData;
import callhub.connect.use_case.message.MessageInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc
public class MessageInteractorTests {

    @MockBean
    public MessageRepository messageRepository;
    @MockBean
    public SessionRepository sessionRepository;

    @InjectMocks
    @Autowired
    private MessageInteractor messageInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewMessage() {
        MessageInputData inputData = new MessageInputData("sessionId", "Hello", Sender.CUSTOMER);
        Message mockMessage = new Message("Hello", "sessionId", Sender.CUSTOMER);
        Session mockSession = new Session(true, "ABCD");

        when(messageRepository.save(any())).thenReturn(mockMessage);
        when(sessionRepository.save(any())).thenReturn(mockSession);
        when(sessionRepository.findById(any())).thenReturn(Optional.of(mockSession));

        HashMap<String, String> response = messageInteractor.newMessage(inputData);

        assertEquals(response.get("message"), "Hello");
    }
}
