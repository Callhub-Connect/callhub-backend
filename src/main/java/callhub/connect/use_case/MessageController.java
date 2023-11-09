package callhub.connect.use_case;

import callhub.connect.data_access.MessageRepository;
import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.Message;
import callhub.connect.entities.Sender;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    public MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @PostMapping("/customer/send")
    public ResponseEntity<String> customerSendMessage(@RequestParam("message") String messageString) {
        HttpHeaders headers = new HttpHeaders();

        // Create message object
        Message message = new Message(messageString, Sender.CUSTOMER);

        // Send to representative via websocket


        // Store to repository
        messageRepository.save(message);

        return new ResponseEntity<>("Message sent", headers, HttpStatus.OK);
    }

    @PostMapping("/employee/send")
    public ResponseEntity<String> employeeSendMessage(@RequestParam("message") String messageString) {
        HttpHeaders headers = new HttpHeaders();

        // Create message object
        Message message = new Message(messageString, Sender.EMPLOYEE);

        // Send to representative via websocket


        // Store to repository
        messageRepository.save(message);

        return new ResponseEntity<>("Message sent", headers, HttpStatus.OK);
    }

}
