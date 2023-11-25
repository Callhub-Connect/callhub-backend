package callhub.connect.use_case.session;

import java.util.HashMap;

public class SessionOutputData {
    private final HashMap<String, String> responseBody;
    public SessionOutputData(HashMap<String, String> responseBody) {this.responseBody = responseBody;}
    public HashMap<String, String> getResponseBody() {return this.responseBody;}
}
