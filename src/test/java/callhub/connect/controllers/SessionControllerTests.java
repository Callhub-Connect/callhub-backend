package callhub.connect.controllers;

import callhub.connect.data_access.DocumentRepository;
import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Session;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTests {

    @MockBean
    public SessionRepository sessionRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createSessionTest() throws Exception {
        when(sessionRepository.save(any())).thenReturn(new Session(
                true,
                "ABCDEF"
        ));

        RequestBuilder request = get("/session/new-session?code=ABCDEF");
        mockMvc.perform(request).andExpect(status().isOk());
    }


}
