package callhub.connect.interface_adapter.email;

import callhub.connect.use_case.email.EmailInputBoundary;
import callhub.connect.use_case.email.EmailInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailInputBoundary emailInteractor;

    @GetMapping("/transcript/{id}")
    public ResponseEntity<String> getTranscript(@PathVariable String id) {
        EmailInputData inputData = new EmailInputData(id);
        return emailInteractor.getTranscript(inputData);
    }

    @GetMapping("/date/{id}")
    public ResponseEntity<String> getCode(@PathVariable String id) {
        EmailInputData inputData = new EmailInputData(id);
        return emailInteractor.getDate(inputData);
    }
}
