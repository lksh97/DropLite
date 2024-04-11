package com.example.filestorage.controller;

import com.example.filestorage.model.FileMetadata;
import com.example.filestorage.repository.FileRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    private final String UPLOAD_DIR = "./uploads/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File savedFile = new File(UPLOAD_DIR + file.getOriginalFilename());
            file.transferTo(savedFile);

            FileMetadata metadata = new FileMetadata();
            metadata.setFilename(file.getOriginalFilename());
            metadata.setFiletype(file.getContentType());
            metadata.setFilesize(file.getSize());
            fileRepository.save(metadata);

            return "File uploaded successfully: "+ file.getOriginalFilename();
        } catch (IOException e) {
            return "Could not upload the file: "+ file.getOriginalFilename() + "!";
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
            InputStreamResource resource = new InputStreamResource(new FileInputStream(fileToDownload));

            // Set the content type and attachment header.
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getName() + "\"")
                    .body(resource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
