package com.example.distributed_file_storage.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.distributed_file_storage.model.FileEntity;
import com.example.distributed_file_storage.model.User;
import com.example.distributed_file_storage.repository.FileRepository;
import com.example.distributed_file_storage.repository.UserRepository;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins="http://localhost:5173")
public class FileController {
    
    private static final String UPLOAD_DIR = "storage/";

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    //upload a file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,Authentication authentication) throws IOException {

        String username = authentication.getName();
        User user = userRepository.findByusername(username).orElseThrow();

        File directory = new File(UPLOAD_DIR);
        if(!directory.exists()){
            directory.mkdirs();
        }
        java.nio.file.Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setSize(file.getSize());
        fileEntity.setFilepath(path.toString());
        fileEntity.setMimeType(file.getContentType());
        fileEntity.setOwner(user);

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
    public List<FileEntity> listFiles(Authentication authentication){
        User user = userRepository.findByusername(authentication.getName()).orElseThrow();
        return fileRepository.findByuser(user);
    }

    //delete file
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) throws IOException {
        Optional<FileEntity> fileOptional = fileRepository.findById(id);

        if(fileOptional.isPresent()){
            FileEntity fileEntity = fileOptional.get();
            File file = new File(fileEntity.getFilepath());

            if(file.exists()){
                file.delete();
            }
            
            fileRepository.deleteById(id);

            return ResponseEntity.ok("File deleted successfully");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //search file
    @GetMapping("/search")
    public List<FileEntity> searchFile(@RequestParam(required=false) String name, @RequestParam(required=false) String type) {
        
        if(name != null){
            return fileRepository.findByfilenameContainingIgnoreCase(name);
        }else if(type != null){
            return fileRepository.findBymimeType(type);
        }
        return fileRepository.findAll();
    }
}
