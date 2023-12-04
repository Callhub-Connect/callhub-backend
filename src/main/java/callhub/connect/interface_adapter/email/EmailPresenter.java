package callhub.connect.interface_adapter.email;

import callhub.connect.use_case.email.EmailOutputBoundary;
import callhub.connect.use_case.email.EmailOutputData;
import org.springframework.stereotype.Service;

@Service
public class EmailPresenter implements EmailOutputBoundary {

    @Override
    public String getResponse(EmailOutputData outputData) {
        return outputData.getResponseBody();
    }
}
