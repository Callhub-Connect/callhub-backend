package callhub.connect;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String home() {
        return "meowwwwwww :3";
    }
}