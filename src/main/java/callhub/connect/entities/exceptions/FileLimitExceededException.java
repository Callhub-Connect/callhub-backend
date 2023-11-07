package callhub.connect.entities.exceptions;

import java.io.IOException;

public class FileLimitExceededException extends IOException {
    public FileLimitExceededException(String message) {
        super(message);
    }
}
