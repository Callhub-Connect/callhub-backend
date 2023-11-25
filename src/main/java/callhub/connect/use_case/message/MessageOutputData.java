package callhub.connect.use_case.message;

import java.util.HashMap;

public class MessageOutputData {
    private final HashMap<String, String> responseBody;

    public MessageOutputData(HashMap<String, String> responseBody) {this.responseBody = responseBody;}

    public HashMap<String, String> getResponseBody() {return this.responseBody;}
}
