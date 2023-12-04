package callhub.connect.interface_adapter.session;

import callhub.connect.entities.Session;
import callhub.connect.use_case.session.SessionInputBoundary;
import callhub.connect.use_case.session.SessionInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionInputBoundary sessionInteractor;
    /**
     * Initiates the creation of a new session and returns the response as a ResponseEntity containing a string representation of the new session's data.
     *
     * @return ResponseEntity containing the response data for the new session with an HTTP status indicating success.
     */
    @GetMapping("/new-session")
    public ResponseEntity<String> newSession() {
        return sessionInteractor.newSession();
    }

    /**
     * Initiates the process of joining an existing session with the provided session code and returns the response as a ResponseEntity containing a string representation of the session's data.
     *
     * @param code The unique identifier (code) of the session to join.
     * @return ResponseEntity containing the response data for joining the session with an HTTP status indicating success.
     */
    @GetMapping("/join/{code}")
    public ResponseEntity<String> joinSession(@PathVariable String code) {
        SessionInputData inputData = new SessionInputData(code);
        return sessionInteractor.joinSession(inputData);
    }

    @GetMapping("/end-session/{code}")
    public ResponseEntity<String> endSession(@PathVariable String code) {
        SessionInputData inputData = new SessionInputData(code);
        return sessionInteractor.endSession(inputData);
    }
}
