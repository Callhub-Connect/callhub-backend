package callhub.connect.use_case.email;

public class EmailOutputData {
    private final String responseBody;
    public EmailOutputData(String responseBody) {
        this.responseBody = responseBody;
    }
    public String getResponseBody() {
        return this.responseBody;
    }
}
