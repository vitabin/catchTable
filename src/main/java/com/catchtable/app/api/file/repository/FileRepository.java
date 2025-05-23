package com.catchtable.app.api.file.repository;


import com.catchtable.app.api.file.domain.FileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByUuid(String uuid);
}
