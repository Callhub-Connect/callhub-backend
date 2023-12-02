//package callhub.connect.use_case;
//
//import callhub.connect.data_access.SessionRepository;
//import callhub.connect.entities.Message;
//import callhub.connect.entities.Session;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//
//@RestController
//@RequestMapping("/email")
//public class EmailController_fromthepast {
//
//    public SessionRepository sessionRepository;
//
//    public EmailController_fromthepast(SessionRepository sessionRepository) {
//        this.sessionRepository = sessionRepository;
//    }
//
//    /**
//     * Retrieves the transcript of messages for a given session code.
//     *
//     * @param id The session code to identify the session.
//     * @return A ResponseEntity containing the transcript of messages as a String.
//     * If the session is not found, an error occurs.
//     */
//    @GetMapping("/transcript/{id}")
//    public ResponseEntity<String> getTranscript(@PathVariable String id) {
//        HttpHeaders headers = new HttpHeaders();
//        boolean sessionExists = sessionRepository.existsById(id);
//        if (!sessionExists) {
//            return new ResponseEntity<>("This session is inactive or does not exist.", headers, HttpStatus.NOT_FOUND);
//        }
//
//        Session session = sessionRepository.getSessionById(id);
//        ArrayList<Message> messagesList = session.getMessages();
//
//        StringBuilder transcript = new StringBuilder();
//        for (Message message : messagesList) {
//            transcript.append(message.formattedMessage()).append("\n");
//        }
//        return new ResponseEntity<>(transcript.toString(), headers, HttpStatus.OK);
//    }
//
//    /**
//     * Retrieves the date messages were sent for a given session code, assuming all messages
//     * were sent on the same day as the first message.
//     *
//     * @param id The session code to identify the session.
//     * @return A ResponseEntity containing the date of messages as a String.
//     * If the session is not found, an error occurs.
//     */
//    @GetMapping("/date/{id}")
//    public ResponseEntity<String> getDate(@PathVariable String id) {
//        HttpHeaders headers = new HttpHeaders();
//        boolean sessionExists = sessionRepository.existsById(id);
//        if (!sessionExists) {
//            new ResponseEntity<>("This session is inactive or does not exist.", headers, HttpStatus.NOT_FOUND);
//        }
//
//        try {
//            Session session = sessionRepository.getSessionById(id);
//            ArrayList<Message> messagesList = session.getMessages();
//
//            Message firstMessage = messagesList.get(0);
//            return new ResponseEntity<>(firstMessage.getDateString(), headers, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
//        }
//    }
//}