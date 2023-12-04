package callhub.connect.interface_adapter.file_adapter;

import callhub.connect.use_case.file.FileOutputBoundary;
import callhub.connect.use_case.file.FileOutputData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FilePresenter implements FileOutputBoundary {

    public ResponseEntity<Object> getResponse(FileOutputData fileOutputData) {
        return fileOutputData.getResponse();
    }

    @Override
    public ResponseEntity<Object> sendResponse(FileOutputData outputData) {
        return outputData.getResponse();
    }
}
