package com.catchtable.util.file.implement;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import com.catchtable.api.file.domain.FileType;
import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.util.file.interfaces.CouponFileParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CsvCouponFileParser implements CouponFileParser {

    @Override
    public void validate(UploadCouponParam uploadCouponParam) {
        MultipartFile file = uploadCouponParam.file();

        try (InputStream inputStream = file.getInputStream();
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeader())) {

            Map<String, Integer> headerMap = parser.getHeaderMap();
            if (!headerMap.containsKey("customer_id")) {
                throw new FileException(FileErrorCode.HEADER_NOT_FOUND, "customer_id");
            }

            String recordStr = null;

            for (CSVRecord record : parser) {
                recordStr = record.get(headerMap.get("customer_id"));
                break;
            }

            if (recordStr == null) {
                throw new FileException(FileErrorCode.EMPTY_FILE);
            }
        } catch (IOException e) {
            throw new FileException(FileErrorCode.IO_EXCEPTION);
        }
    }

    @Override
    public List<String> getRow(InputStream fileInputStream, Integer num) {
        try (Reader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeader())) {

            List<String> row = new ArrayList<>();
            row.add("customer_id");
            int i = 0;
            for (CSVRecord record : parser) {
                row.add(record.get("customer_id"));
                i++;
                if (i == num) {
                    break;
                }
            }

            return row;
        } catch (IOException e) {
            throw new FileException(FileErrorCode.IO_EXCEPTION);
        }
    }

    @Override
    public Set<FileType> getSupportedFileTypes() {
        return Set.of(FileType.CSV);
    }
}

