package callhub.connect.interface_adapter;

import callhub.connect.use_case.session.SessionOutputBoundary;
import callhub.connect.use_case.session.SessionOutputData;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SessionPresenter implements SessionOutputBoundary {
    @Override
    public HashMap<String, String> getResponse(SessionOutputData outputData) {
        return outputData.getResponseBody();
    }
}
