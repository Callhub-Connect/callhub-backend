package callhub.connect.use_case.message;

import java.util.HashMap;

public interface MessageOutputBoundary {
    HashMap<String, String> getMessage(MessageOutputData outputData);
}
