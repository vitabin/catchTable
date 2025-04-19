package com.catchtable.util.file.interfaces;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import java.io.InputStream;
import java.util.List;

public interface CouponFileParser {

    void validate(UploadCouponParam uploadCouponParam);

    List<String> getRow(InputStream fileInputStream, Integer num);
}
