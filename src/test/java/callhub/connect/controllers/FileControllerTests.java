package callhub.connect.controllers;

import callhub.connect.data_access.DataAccess;
import callhub.connect.data_access.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMultipartHttpServletRequestDsl;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTests {
    @MockBean
    public DocumentRepository documentRepository;
    HttpHeaders headers = new HttpHeaders();
    DataAccess dataAccessObject;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testUploadPDFLocal() {
    }

    @Test // Cannot invoke "callhub.connect.entities.FileDocument.getId()" because "result" is null
    void testUploadFile_Mock() throws Exception {
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                "sample-file-mock.pdf",
                "application/pdf",
                "This is the file content".getBytes());

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/upload_network");
        mockMvc.perform(multipartRequest.file(sampleFile).param("name", "test")).andExpect(status().isOk());
    }

    @Test
    void testUploadFile_Fall2023() throws Exception {
        String currentDirectory = System.getProperty("user.dir");
        String pathName = currentDirectory + "/src/main/java/callhub/connect/pdfs/fall2023.pdf";
        File pdfFile = new File(pathName);
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                pdfFile.getName(),
                "application/pdf",
                new FileInputStream(pdfFile));

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/upload_network");
        mockMvc.perform(multipartRequest.file(sampleFile).param("name", "test")).andExpect(status().isOk());
    }

    @Test
    void testUploadFile_NoFile() throws Exception {
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/upload_network");
        mockMvc.perform(multipartRequest).andExpect(status().isBadRequest());
    }

    @Test
    void testFindDocumentByID() {
    }
}