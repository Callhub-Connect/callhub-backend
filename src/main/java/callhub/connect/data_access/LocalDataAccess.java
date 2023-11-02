package callhub.connect.data_access;
import callhub.connect.entities.exceptions.FileLimitExceededException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.bson.types.Binary;

public class LocalDataAccess {
    public static byte[] convertPDFToByteArray(String filePath) throws IOException {
        File pdfFile = new File(filePath);
        // 1 mb = 1024 * 1024

        try (FileInputStream fileInputStream = new FileInputStream(pdfFile)) {
            if (pdfFile.length() > 16 * 1024 * 1024) {
                throw new FileLimitExceededException("PDF document exceeds 16MB.");
            }

            byte[] pdfBytes = new byte[(int) pdfFile.length()];
            int bytesRead = fileInputStream.read(pdfBytes);
            if (bytesRead < 0) {
                throw new IOException("Failed to read PDF file");
            }
            return pdfBytes;
        }
    }

    public static Binary byteArrayToBinary(byte[] byteArray) {
        return new Binary(byteArray);
    }
}
