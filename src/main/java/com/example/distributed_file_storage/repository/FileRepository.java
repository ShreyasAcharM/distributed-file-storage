package com.example.distributed_file_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.distributed_file_storage.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long>{

}
