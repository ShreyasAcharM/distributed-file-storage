package com.example.distributed_file_storage.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.distributed_file_storage.model.FileEntity;
import com.example.distributed_file_storage.repository.FileRepository;

@RestController
@RequestMapping("/files")
public class FileController {
    
    private static final String UPLOAD_DIR = "storage/";

    @Autowired
    private FileRepository fileRepository;

    //upload a file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if(!directory.exists()){
            directory.mkdirs();
        }
        java.nio.file.Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        FileEntity fileEntity = new FileEntity(
            file.getOriginalFilename(),
            path.toString(),
            file.getContentType(),
            file.getSize()
        );
        fileRepository.save(fileEntity);

        return "File uploaded ! - " + file.getOriginalFilename();
    }

    //download a file
    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) throws IOException{
        Path path = Paths.get(UPLOAD_DIR + filename);

        if(!Files.exists(path)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] fileData = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        String mime = Files.probeContentType(path);
        if (mime != null) {
            headers.add(HttpHeaders.CONTENT_TYPE, mime);
        }

        return new ResponseEntity<>(fileData,headers,HttpStatus.OK);
    }

    //file list
    @GetMapping("/list")
    public List<FileEntity> listFiles(){
        return fileRepository.findAll();
    }
}
