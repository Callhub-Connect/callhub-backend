package callhub.connect;

import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static callhub.connect.entities.Sender.CUSTOMER;
import static callhub.connect.entities.Sender.EMPLOYEE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmailTests {

    @MockBean
    public SessionRepository sessionRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getTranscriptSuccess() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");
        mockSession.setId("abc123adsbksdhlsad");
        LocalDateTime timeStamp = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 9, 48);
        LocalDateTime timeStamp2 = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 11, 12);
        Message message1 = new Message("Test Message", timeStamp, mockSession.getId(), CUSTOMER);
        Message message2 = new Message("Reply Message", timeStamp2, mockSession.getId(), EMPLOYEE);
        mockSession.addMessage(message1);
        mockSession.addMessage(message2);
        when(sessionRepository.existsById(anyString())).thenReturn(true);
        when(sessionRepository.getSessionById(anyString())).thenReturn(mockSession);
        String expected = "17:09:48 CUSTOMER: Test Message\n17:11:12 EMPLOYEE: Reply Message\n";

        String Url = "/email/transcript/" + mockSession.getId();
        mockMvc.perform(get(Url)).andExpect(status().isOk()).andExpect(content().string(expected));
    }

    @Test
    void getTranscriptDNE() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");
        mockSession.setId("abc123adsbksdhlsad");
        LocalDateTime timeStamp = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 9, 48);
        LocalDateTime timeStamp2 = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 11, 12);
        Message message1 = new Message("Test Message", timeStamp, mockSession.getId(), CUSTOMER);
        Message message2 = new Message("Reply Message", timeStamp2, mockSession.getId(), EMPLOYEE);
        mockSession.addMessage(message1);
        mockSession.addMessage(message2);
        when(sessionRepository.existsById(anyString())).thenReturn(false);
        String expected = "This session is inactive or does not exist.";

        String Url = "/email/transcript/" + mockSession.getId();
        mockMvc.perform(get(Url)).andExpect(status().isOk()).andExpect(content().string(expected));
    }

    @Test
    void getCode() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");
        mockSession.setId("abc123adsbksdhlsad");
        LocalDateTime timeStamp = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 9, 48);
        LocalDateTime timeStamp2 = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 11, 12);
        Message message1 = new Message("Test Message", timeStamp, mockSession.getId(), CUSTOMER);
        Message message2 = new Message("Reply Message", timeStamp2, mockSession.getId(), EMPLOYEE);
        mockSession.addMessage(message1);
        mockSession.addMessage(message2);
        when(sessionRepository.existsById(anyString())).thenReturn(true);
        when(sessionRepository.getSessionById(anyString())).thenReturn(mockSession);
        String expected = "December 03, 2023";

        String Url = "/email/date/" + mockSession.getId();
        mockMvc.perform(get(Url)).andExpect(status().isOk()).andExpect(content().string(expected));
    }

    @Test
    void getCodeDNE() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");
        mockSession.setId("abc123adsbksdhlsad");
        LocalDateTime timeStamp = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 9, 48);
        LocalDateTime timeStamp2 = LocalDateTime.of(2023, Month.DECEMBER, 3, 17, 11, 12);
        Message message1 = new Message("Test Message", timeStamp, mockSession.getId(), CUSTOMER);
        Message message2 = new Message("Reply Message", timeStamp2, mockSession.getId(), EMPLOYEE);
        mockSession.addMessage(message1);
        mockSession.addMessage(message2);
        when(sessionRepository.existsById(anyString())).thenReturn(false);
        String expected = "This session is inactive or does not exist.";

        String Url = "/email/date/" + mockSession.getId();
        mockMvc.perform(get(Url)).andExpect(status().isOk()).andExpect(content().string(expected));
    }
}