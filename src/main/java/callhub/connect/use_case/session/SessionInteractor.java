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

    @Override
    public ResponseEntity<String> newSession() {
        HttpHeaders headers = new HttpHeaders();
        HashMap<String, String> responseBody = sessionDataAccessObject.generateNewSession();
        SessionOutputData outputData = new SessionOutputData(responseBody);
        return new ResponseEntity<>(gson.toJson(sessionPresenter.getResponse(outputData)), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> joinSession(SessionInputData inputData) {
        HttpHeaders headers = new HttpHeaders();
        HashMap<String, String> responseBody = sessionDataAccessObject.joinSession(inputData.getCode());
        return new ResponseEntity<>(gson.toJson(responseBody), headers, HttpStatus.OK);
    }
}
