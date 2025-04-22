package com.catchtable.util.file.classes;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.util.file.interfaces.CouponFileParser;
import com.monitorjbl.xlsx.StreamingReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("excel")
public class ExcelCouponFileParser implements CouponFileParser {

    @Override
    public void validate(UploadCouponParam uploadCouponParam) {
        MultipartFile file = uploadCouponParam.file();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = StreamingReader.builder()
                                               .rowCacheSize(10)
                                               .bufferSize(4096)
                                               .open(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                throw new FileException(FileErrorCode.NO_HEADER_FOUND);
            }
            rowIterator.next();

            if (!rowIterator.hasNext()) {
                throw new FileException(FileErrorCode.EMPTY_FILE);
            }
        } catch (IOException e) {
            throw new FileException(FileErrorCode.IO_EXCEPTION);
        }
    }

    @Override
    public List<String> getRow(InputStream fileInputStream, Integer num) {
        try (Workbook workbook = StreamingReader.builder()
                                                .rowCacheSize(10)
                                                .bufferSize(4096)
                                                .open(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<String> list = new ArrayList<>();

            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                StringBuilder columns = new StringBuilder();

                while (cellIterator.hasNext()) {
                    columns.append(cellIterator.next()
                                               .getStringCellValue())
                           .append(",");
                }

                columns.append("\n");
                list.add(columns.toString());

                if (list.size() == num) {
                    break;
                }
            }
            
            return list;
        } catch (IOException e) {
            throw new FileException(FileErrorCode.IO_EXCEPTION);
        }
    }
}
