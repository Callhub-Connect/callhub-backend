package callhub.connect.controllers;

import callhub.connect.data_access.DocumentRepository;
import callhub.connect.data_access.SessionRepository;
import callhub.connect.entities.FileDocument;
import callhub.connect.entities.Session;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTests {
    @MockBean
    public DocumentRepository documentRepository;

    @MockBean
    public SessionRepository sessionRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testUploadPDFLocal() throws Exception {
        when(documentRepository.save(any())).thenReturn(new FileDocument(
                "test123",
                new Binary("sample file content".getBytes()),
                LocalDate.now()));

        RequestBuilder request = get("/files/upload_local?name=test123");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void testUploadPDFLocal_NoName() throws Exception {
        RequestBuilder request = get("/files/upload_local");
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void testUploadFile_Mock() throws Exception {
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                "sample-file-mock.pdf",
                "application/pdf",
                "This is the file content".getBytes());

        // documentRepository is a mock, not real, so we need to mock the save() method - by default it returns null
        when(documentRepository.save(any())).thenReturn(new FileDocument(
                "MockFile",
                new Binary("This is the file content".getBytes()),
                LocalDate.now()));

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/upload_network");
        mockMvc.perform(multipartRequest.file(sampleFile).param("name", "MockFile")).andExpect(status().isOk());
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

        when(documentRepository.save(any())).thenReturn(new FileDocument(
                "MockFile",
                new Binary("sample pdf content".getBytes()),
                LocalDate.now()));

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/upload_network");
        mockMvc.perform(multipartRequest.file(sampleFile).param("name", "Fall2023")).andExpect(status().isOk());
    }

    @Test
    void testUploadFile_NoFile() throws Exception {
        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/upload_network");
        mockMvc.perform(multipartRequest).andExpect(status().isBadRequest());
    }

    @Test
    void testFindDocumentByID() throws Exception {
        FileDocument fileDocument = new FileDocument(
                "MockFile",
                new Binary("This is the file content".getBytes()),
                LocalDate.now());

        fileDocument.setId("123314");

        when(documentRepository.findById(any())).thenReturn(Optional.of(fileDocument));

        RequestBuilder request = get("/files/{id}", fileDocument.getId());
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE));
    }

    @Test
    void testFindDocumentByID_InvalidID() throws Exception {
        RequestBuilder request = get("/files/12345678");
        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(content().string("File could not be found."));
    }

    @Test
    void testPDFLinkValidSession() throws Exception {
        String currentDirectory = System.getProperty("user.dir");
        String pathName = currentDirectory + "/src/main/java/callhub/connect/pdfs/fall2023.pdf";
        File pdfFile = new File(pathName);
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                pdfFile.getName(),
                "application/pdf",
                new FileInputStream(pdfFile));


        when(documentRepository.save(any())).thenReturn(new FileDocument(
                "MockFile",
                new Binary("sample pdf content".getBytes()),
                LocalDate.now()));

        Session mockSession = new Session(true, "ABCDEF");

        // Mock the behavior of the sessionRepository.save() method
        when(sessionRepository.getSessionsByActiveAndCode(true, "ABCDEF")).thenReturn(mockSession);

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/session_add_pdf");
        mockMvc.perform(multipartRequest.file(sampleFile).param("session", "ABCDEF").param("name", "MockFile")).andExpect(status().isOk());
    }

    @Test
    void testPDFLinkInvalidSession() throws Exception {
        String currentDirectory = System.getProperty("user.dir");
        String pathName = currentDirectory + "/src/main/java/callhub/connect/pdfs/fall2023.pdf";
        File pdfFile = new File(pathName);
        MockMultipartFile sampleFile = new MockMultipartFile(
                "file",
                pdfFile.getName(),
                "application/pdf",
                new FileInputStream(pdfFile));


        when(documentRepository.save(any())).thenReturn(new FileDocument(
                "MockFile",
                new Binary("sample pdf content".getBytes()),
                LocalDate.now()));

        Session mockSession = new Session(true, "ABCDEF");

        // Mock the behavior of the sessionRepository.save() method
        when(sessionRepository.getSessionsByActiveAndCode(true, "ABCDEF")).thenReturn(mockSession);

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/files/session_add_pdf");
        mockMvc.perform(multipartRequest.file(sampleFile).param("session", "123456").param("name", "MockFile")).andExpect(status().isBadRequest());
    }
}