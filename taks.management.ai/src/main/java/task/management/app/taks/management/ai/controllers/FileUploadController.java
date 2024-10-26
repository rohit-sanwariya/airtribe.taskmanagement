package task.management.app.taks.management.ai.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        String user =  authentication.getName();
        String username = user.split("@")[0];
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDirectory + "/" + username);
            Files.createDirectories(uploadPath);

            // Get file name and extension
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));

            // Save file to upload directory
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
}