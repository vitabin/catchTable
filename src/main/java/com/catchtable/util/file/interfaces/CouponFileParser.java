package com.catchtable.util.file.interfaces;

import com.catchtable.app.api.admin.DTO.UploadCouponParam;
import com.catchtable.app.api.file.domain.FileType;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface CouponFileParser {

    void validate(UploadCouponParam uploadCouponParam);

    List<String> getRow(InputStream fileInputStream, Integer num);

    Set<FileType> getSupportedFileTypes();
}
