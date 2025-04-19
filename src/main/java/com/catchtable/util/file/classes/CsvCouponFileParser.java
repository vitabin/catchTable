package com.catchtable.util.file.classes;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import com.catchtable.util.file.interfaces.CouponFileParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("csv")
public class CsvCouponFileParser implements CouponFileParser {

    @Override
    public void validation(UploadCouponParam uploadCouponParam) {
        MultipartFile file = uploadCouponParam.file();

        try (InputStream inputStream = file.getInputStream();
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeader())) {

            Map<String, Integer> headerMap = parser.getHeaderMap();
            if (!headerMap.containsKey("customer_id")) {
                throw new RuntimeException("Missing header: customer_id");
            }

            String record = parser.getRecords()
                                  .get(0)
                                  .get(headerMap.get("customer_id"));

            if (record == null) {
                throw new RuntimeException("No records found");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getRow(InputStream fileInputStream, Integer num) {
        try (Reader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeader())) {

            List<CSVRecord> records = parser.getRecords();
            List<String> row = new ArrayList<>();
            row.add("customer_id");

            for (int i = 0; i < num; i++) {
                row.add(records.get(i)
                               .get("customer_id") + "\n");
            }

            return row;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

