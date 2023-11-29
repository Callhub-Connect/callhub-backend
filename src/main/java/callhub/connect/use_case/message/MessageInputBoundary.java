package callhub.connect.use_case.message;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface MessageInputBoundary {
    HashMap<String, String> newMessage(MessageInputData inputData);
}
