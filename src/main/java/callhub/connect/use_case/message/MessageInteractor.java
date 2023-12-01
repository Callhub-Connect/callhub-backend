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

    /**
     * Constructs a MessageInteractor with the provided dependencies.
     *
     * @param messageDataAccessObject The data access object responsible for managing messages.
     * @param messagePresenter        The presenter responsible for formatting and presenting messages.
     */
    public MessageInteractor(MessageDataAccessInterface messageDataAccessObject, MessageOutputBoundary messagePresenter) {
        this.messageDataAccessObject = messageDataAccessObject;
        this.messagePresenter = messagePresenter;
    }


    /**
     * Creates a new message using the provided input data, sends it to the database, and returns the response data as a HashMap.
     *
     * @param inputData The MessageInputData object containing the message content, session ID, and sender information.
     * @return A HashMap containing the response data for the new message.
     */
    @Override
    public HashMap<String, String> newMessage(MessageInputData inputData) {
        HashMap<String, String> response = messageDataAccessObject.generateResponse(inputData.getMessage());
        messageDataAccessObject.sendResponseToDatabase(inputData.getMessage(), inputData.getSessionId(), inputData.getSender());
        MessageOutputData outputData = new MessageOutputData(response);
        return outputData.getResponseBody();
    }
}
