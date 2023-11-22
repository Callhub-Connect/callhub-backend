package callhub.connect.use_case;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class MessageInteractor {
    final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss a", Locale.CANADA);
    public HashMap<String, String> generateResponse(String message){
        HashMap<String, String> response = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();

        response.put("message", message);
        response.put("timestamp", timestamp.format(TIME_FORMATTER).replace("a.m.", "AM").replace("p.m.","PM"));

        return response;
    }
}
