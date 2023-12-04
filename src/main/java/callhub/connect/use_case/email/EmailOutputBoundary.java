package callhub.connect.use_case.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailOutputBoundary {
    String getResponse(EmailOutputData outputData);
}
