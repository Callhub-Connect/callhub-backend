package callhub.connect.use_case.email;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface EmailDataAccessInterface {

    String getTranscript(String id);

    String getDate(String id);
}
