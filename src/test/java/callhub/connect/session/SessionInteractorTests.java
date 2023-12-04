package callhub.connect.session;

import callhub.connect.entities.Session;
import callhub.connect.use_case.session.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SessionInteractorTests {

    @Mock
    private SessionDataAccessInterface sessionDataAccessObject;

    @Mock
    private SessionOutputBoundary sessionPresenter;

    @InjectMocks
    private SessionInteractor sessionInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewSession() {
        when(sessionDataAccessObject.generateNewSession()).thenReturn(new HashMap<>());
        when(sessionPresenter.getResponse(any())).thenReturn(new HashMap<>());

        ResponseEntity<String> responseEntity = sessionInteractor.newSession();

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(sessionDataAccessObject, times(1)).generateNewSession();
        verify(sessionPresenter, times(1)).getResponse(any());
    }

    @Test
    void testConstructorAndGetResponseBody() {
        // Arrange
        HashMap<String, String> responseBody = new HashMap<>();

        // Act
        SessionOutputData sessionOutputData = new SessionOutputData(responseBody);

        // Assert
        assertEquals(responseBody, sessionOutputData.getResponseBody());
    }

    @Test
    void testJoinSession() {
        SessionInputData inputData = new SessionInputData("sessionCode");

        when(sessionDataAccessObject.joinSession("sessionCode")).thenReturn(new HashMap<>());

        ResponseEntity<String> responseEntity = sessionInteractor.joinSession(inputData);

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(sessionDataAccessObject, times(1)).joinSession("sessionCode");
    }

    @Test
    void testEndSession() {
        SessionInputData inputData = new SessionInputData("sessionCode");

        when(sessionDataAccessObject.endSession("sessionCode")).thenReturn(new HashMap<>());

        ResponseEntity<String> responseEntity = sessionInteractor.endSession(inputData);

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(sessionDataAccessObject, times(1)).endSession("sessionCode");
    }
}
