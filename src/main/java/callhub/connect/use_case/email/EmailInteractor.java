package callhub.connect.use_case.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailInteractor implements EmailInputBoundary {

    @Autowired
    final EmailDataAccessInterface emailDataAccessObject;

    @Autowired
    final EmailOutputBoundary emailPresenter;

    public EmailInteractor(EmailDataAccessInterface emailDataAccessObject, EmailOutputBoundary emailPresenter) {
        this.emailDataAccessObject = emailDataAccessObject;
        this.emailPresenter = emailPresenter;
    }

    @Override
    public ResponseEntity<String> getTranscript(EmailInputData inputData) {
        HttpHeaders headers = new HttpHeaders();
        String transcript = emailDataAccessObject.getTranscript(inputData.getId());
        EmailOutputData outputData = new EmailOutputData(transcript);
        return new ResponseEntity<>(emailPresenter.getResponse(outputData), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getDate(EmailInputData inputData) {
        HttpHeaders headers = new HttpHeaders();
        String date = emailDataAccessObject.getDate(inputData.getId());
        EmailOutputData outputData = new EmailOutputData(date);
        return new ResponseEntity<>(emailPresenter.getResponse(outputData), headers, HttpStatus.OK);
    }
}
