package callhub.connect.use_case.message;

import java.util.HashMap;

public interface MessageDataAccessInterface {
    public HashMap<String, String> generateResponse(String message);
}
