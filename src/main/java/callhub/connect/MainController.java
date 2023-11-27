package callhub.connect;

import callhub.connect.data_access.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@EnableMongoRepositories
public class MainController {
    @Autowired
    MessageRepository messageRepository;
    @GetMapping("/")
    public String home() {
        return "New New Deployment!";
    }
}
