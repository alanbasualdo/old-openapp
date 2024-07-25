package com.opencars.netgo.uploadfiles;

import com.opencars.netgo.uploadfiles.services.FileServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @InjectMocks
    private FileServiceImp fileService;

    @Test
    public void testSave() throws IOException {
        // Prepare test data
        String fileName = "example.txt";
        String originalFileName = "example.txt";
        String contentType = "text/plain";
        byte[] content = "Hello, World!".getBytes();
        MultipartFile file = new MockMultipartFile(fileName, originalFileName, contentType, content);
        String type = "usuarios";
        String id = "15000";
        String interno = "no";

        // Call the method under test
        fileService.save(file, type, id, interno);

        // Verify that the file was saved in the correct directory
        Path expectedPath = Paths.get("data/usuarios/15000/example.txt");
        assertTrue(Files.exists(expectedPath));
    }
}
