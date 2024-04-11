package com.example.filestorage.repository;

import com.example.filestorage.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileMetadata, Long> {

}
