package callhub.connect.data_access;
import callhub.connect.entities.exceptions.FileLimitExceededException;
import org.bson.types.Binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class DataAccess {
    public abstract Binary serializePDF() throws IOException;

    public static byte[] getPDFBytes(File pdfFile) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(pdfFile)) {
            // 1 mb = 1024 * 1024
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
