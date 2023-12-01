package callhub.connect.use_case.session;

import callhub.connect.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SessionInteractor implements SessionInputBoundary{

    @Autowired
    final SessionDataAccessInterface sessionDataAccessObject;

    @Autowired
    final SessionOutputBoundary sessionPresenter;

    private static final Gson gson = new Gson();

    public SessionInteractor(SessionDataAccessInterface sessionDataAccessObject, SessionOutputBoundary sessionPresenter) {
        this.sessionDataAccessObject = sessionDataAccessObject;
        this.sessionPresenter = sessionPresenter;
    }

    /**
     * Creates a new session and returns a ResponseEntity containing the response data.
     *
     * @return ResponseEntity containing the response data for the newly created session with an HTTP status of OK.
     */
    @Override
    public ResponseEntity<String> newSession() {
        HttpHeaders headers = new HttpHeaders();
        HashMap<String, String> responseBody = sessionDataAccessObject.generateNewSession();
        SessionOutputData outputData = new SessionOutputData(responseBody);
        return new ResponseEntity<>(gson.toJson(sessionPresenter.getResponse(outputData)), headers, HttpStatus.OK);
    }

    /**
     * Joins an existing session with the provided session code and returns a ResponseEntity containing the response data.
     *
     * @param inputData The SessionInputData object containing the session code to join.
     * @return ResponseEntity containing the response data for joining the session with an HTTP status of OK.
     */
    @Override
    public ResponseEntity<String> joinSession(SessionInputData inputData) {
        HttpHeaders headers = new HttpHeaders();
        HashMap<String, String> responseBody = sessionDataAccessObject.joinSession(inputData.getCode());
        return new ResponseEntity<>(gson.toJson(responseBody), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> endSession(SessionInputData inputData) {
        HttpHeaders headers = new HttpHeaders();
        HashMap<String, String> responseBody = sessionDataAccessObject.endSession(inputData.getCode());
        return new ResponseEntity<>(gson.toJson(responseBody), headers, HttpStatus.OK);
    }


}
