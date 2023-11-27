package callhub.connect.use_case.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MessageInteractor implements MessageInputBoundary {

    @Autowired
    final MessageDataAccessInterface messageDataAccessObject;

    @Autowired
    final MessageOutputBoundary messagePresenter;

    public MessageInteractor(MessageDataAccessInterface messageDataAccessObject, MessageOutputBoundary messagePresenter) {
        this.messageDataAccessObject = messageDataAccessObject;
        this.messagePresenter = messagePresenter;
    }


    @Override
    public HashMap<String, String> newMessage(MessageInputData inputData) {
        HashMap<String, String> response = messageDataAccessObject.generateResponse(inputData.getMessage());
        MessageOutputData outputData = new MessageOutputData(response);
        return outputData.getResponseBody();
    }
}
