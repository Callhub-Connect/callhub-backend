package callhub.connect;

import callhub.connect.data_access.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@EnableMongoRepositories
public class MainController {

    /**
     * Returns a welcome message for the Callhub application's home page.
     *
     * @return A string containing the welcome message.
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to Callhub";
    }
}
