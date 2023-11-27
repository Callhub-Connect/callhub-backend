package callhub.connect.use_case.session;

import java.util.HashMap;

public interface SessionOutputBoundary {
    HashMap<String, String> getResponse(SessionOutputData outputData);
}
