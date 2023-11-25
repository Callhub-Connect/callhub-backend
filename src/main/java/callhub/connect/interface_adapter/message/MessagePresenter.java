package callhub.connect.interface_adapter.message;

import callhub.connect.use_case.message.MessageOutputBoundary;
import callhub.connect.use_case.message.MessageOutputData;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MessagePresenter implements MessageOutputBoundary {
    @Override
    public HashMap<String, String> getMessage(MessageOutputData outputData) {return outputData.getResponseBody();}
}
