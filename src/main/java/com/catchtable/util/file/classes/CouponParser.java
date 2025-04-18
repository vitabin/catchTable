package com.catchtable.util.file.classes;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import com.catchtable.util.file.interfaces.CouponFileParser;
import java.io.InputStream;
import java.util.List;

public class CouponParser {

    private final CouponFileParser couponFileParser;

    public CouponParser(CouponFileParser couponFileParser) {
        this.couponFileParser = couponFileParser;
    }

    public void validation(UploadCouponParam uploadCouponParam) {
        couponFileParser.validation(uploadCouponParam);
    }

    public List<String> getRow(InputStream inputStream, Integer num) {
        return couponFileParser.getRow(inputStream, num);
    }
}
