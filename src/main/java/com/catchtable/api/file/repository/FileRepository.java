package com.catchtable.api.file.repository;


import com.catchtable.api.file.domain.FileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByUuid(String uuid);
}
