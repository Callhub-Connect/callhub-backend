package callhub.connect.session;

import callhub.connect.data_access.DocumentRepository;
import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Session;
import callhub.connect.interface_adapter.session.SessionController;
import callhub.connect.use_case.session.SessionInputBoundary;
import callhub.connect.use_case.session.SessionInputData;
import callhub.connect.use_case.session.SessionInteractor;
import callhub.connect.use_case.session.*;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    public SessionRepository sessionRepository;


    @Test
    public void createSessionTest() throws Exception {
        when(sessionRepository.save(any())).thenReturn(new Session(
                true,
                "ABCDEF"
        ));

        RequestBuilder request = get("/session/new-session?code=ABCDEF");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void testJoinSession() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");

        when(sessionRepository.getSessionsByActiveAndCode(anyBoolean(), anyString())).thenReturn(mockSession);

        // Perform the request and verify the response
        mockMvc.perform(get("/session/join/ABCDEF"))
                .andExpect(status().isOk());
    }

    @Test
    void testEndSession() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");
        when(sessionRepository.getSessionsByActiveAndCode(anyBoolean(), anyString())).thenReturn(mockSession);

        // Perform the request and verify the response
        mockMvc.perform(get("/session/end-session/ABCDEF"))
                .andExpect(status().isOk());
        assert !mockSession.getIsActive();
    }


    @Test
    void testAllSession() throws Exception {
        Session mockSession = new Session(true, "ABCDEF");

        when(sessionRepository.getSessionsByActiveAndCode(anyBoolean(), anyString())).thenReturn(mockSession);
        when(sessionRepository.save(any())).thenReturn(mockSession);

        RequestBuilder newRequest = get("/session/new-session?code=ABCDEF");
        mockMvc.perform(newRequest).andExpect(status().isOk());

        RequestBuilder joinRequest = get("/session/join/ABCDEF");
        mockMvc.perform(joinRequest).andExpect(status().isOk());

        RequestBuilder endRequest = get("/session/end-session/ABCDEF");
        mockMvc.perform(endRequest).andExpect(status().isOk());
    }
}
