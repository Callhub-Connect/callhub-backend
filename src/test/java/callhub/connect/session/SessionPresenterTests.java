package callhub.connect.session;

import callhub.connect.interface_adapter.session.SessionPresenter;
import callhub.connect.use_case.session.SessionOutputData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionPresenterTests {

    @Test
    void testGetResponse() {
        // Arrange
        SessionPresenter sessionPresenter = new SessionPresenter();
        HashMap<String, String> responseBody = new HashMap<>();
        SessionOutputData outputData = new SessionOutputData(responseBody);

        // Act
        HashMap<String, String> response = sessionPresenter.getResponse(outputData);

        // Assert
        assertEquals(responseBody, response);
    }
}
