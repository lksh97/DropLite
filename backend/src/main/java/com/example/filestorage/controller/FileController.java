package com.example.filestorage.controller;

import com.example.filestorage.model.FileMetadata;
import com.example.filestorage.repository.FileRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    private final static Logger LOGGER = Logger.getLogger(FileController.class.getName());

    public FileController() {
        try {

            FileHandler fileHandler = new FileHandler("app.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        // Create the upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean dirCreated = uploadDir.mkdirs();
            if (!dirCreated) {
                LOGGER.severe("Could not create the directory: " + UPLOAD_DIR);
                throw new RuntimeException("Could not create the upload directory!");
            }
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            if (Files.exists(filePath)) {
                return ResponseEntity.badRequest().body("Error: File with the same name already exists!");
            }

            File savedFile = new File(UPLOAD_DIR + file.getOriginalFilename());
            file.transferTo(savedFile);

            FileMetadata metadata = new FileMetadata();
            metadata.setFilename(file.getOriginalFilename());
            metadata.setFiletype(file.getContentType());
            metadata.setFilesize(file.getSize());
            fileRepository.save(metadata);

            LOGGER.info("File uploaded successfully: " + file.getOriginalFilename());

            return ResponseEntity.ok("File uploaded successfully: "+ file.getOriginalFilename());
        } catch (IOException e) {
            LOGGER.severe("Could not upload the file: " + file.getOriginalFilename() + " due to " + e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: "+ file.getOriginalFilename() + "!");
        }
    }

    @GetMapping("/files")
    public List<FileMetadata> listFiles() {
        return fileRepository.findAll();
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletResponse response) {
        try {
            // Assuming files are stored in the 'uploads' directory
            File fileToDownload = new File(UPLOAD_DIR + filename);
            if (!fileToDownload.exists()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(fileToDownload));

            // Set the content type and attachment header.
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getName() + "\"")
                    .body(resource);
        } catch (FileNotFoundException e) {
            LOGGER.severe("Could not download the file: " + filename + " due to " + e);

            return ResponseEntity.notFound().build();
        }
    }
}
