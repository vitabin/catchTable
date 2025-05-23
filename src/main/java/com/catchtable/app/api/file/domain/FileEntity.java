package com.catchtable.app.api.file.domain;

import com.catchtable.app.api.file.DTO.S3UploadCacheDTO;
import com.catchtable.app.api.file.DTO.UploadFileParam;
import com.catchtable.app.api.user.domain.UserEntity;
import com.catchtable.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "file")
@Getter
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class FileEntity extends BaseEntity {

    @Id
    private String uuid;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    private String filename;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String mimeType = "multipart/form-data";

    private LocalDateTime deletedAt;

    public static FileEntity of(UploadFileParam uploadFileParam, String uuid,
        String relativePath) {
        FileEntity entity = new FileEntity();
        entity.fileType = uploadFileParam.fileType();
        entity.filename = uploadFileParam.filename();
        entity.uuid = uuid;
        entity.user = uploadFileParam.userEntity();
        entity.path = relativePath;
        return entity;
    }

    public static FileEntity of(S3UploadCacheDTO dto, UserEntity user, String uuid,
        String objectKey) {
        FileEntity entity = new FileEntity();
        entity.path = objectKey;
        entity.user = user;
        entity.filename = dto.getFilename();
        entity.fileType = dto.getFileType();
        entity.mimeType = dto.getContentType();
        entity.uuid = uuid;
        return entity;
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }
}
