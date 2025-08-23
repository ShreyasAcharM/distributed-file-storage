package com.example.distributed_file_storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.distributed_file_storage.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long>{

    List<FileEntity> findByfilenameContainingIgnoreCase(String filename);

    List<FileEntity> findBymimeType(String mimeType);

}
