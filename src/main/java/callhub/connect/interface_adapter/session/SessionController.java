package callhub.connect.interface_adapter.session;

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

    @GetMapping("/new-session")
    public ResponseEntity<String> newSession() {
        return sessionInteractor.newSession();
    }

    @GetMapping("/join/{code}")
    public ResponseEntity<String> joinSession(@PathVariable String code) {
        SessionInputData inputData = new SessionInputData(code);
        return sessionInteractor.joinSession(inputData);
    }
}
