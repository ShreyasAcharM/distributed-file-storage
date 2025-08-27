package com.example.distributed_file_storage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String filename;
    private String filepath;
    private String mimeType;
    private long size;
    private LocalDateTime uploadDate;
    
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    public FileEntity() {}

    public FileEntity (String filename, String filepath, String mimeType, Long size, User user) {
        this.filename = filename;
        this.filepath = filepath;
        this.mimeType = mimeType;
        this.size = size;
        this.uploadDate = LocalDateTime.now();
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getFilepath() { return filepath; }
    public void setFilepath(String filepath) { this.filepath = filepath; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public User getOwner(){ return user;}
    public void setOwner(User user){this.user = user;}
}
