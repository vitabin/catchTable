package com.catchtable.util.file.classes;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import com.catchtable.util.file.interfaces.CouponFileParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("excel")
public class ExcelCouponFileParser implements CouponFileParser {

    @Override
    public void validation(UploadCouponParam uploadCouponParam) {
        MultipartFile file = uploadCouponParam.file();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            Cell headerCell = headerRow.getCell(0);

            if (headerCell == null || !headerCell.getStringCellValue()
                                                 .equals("customer_id")) {
                throw new RuntimeException("Missing header: customer_id");
            }

            if (sheet.getLastRowNum() == 1) {
                throw new RuntimeException("No customers found");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getRow(InputStream fileInputStream, Integer num) {
        try (Workbook workbook = WorkbookFactory.create(fileInputStream);) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            Cell headerCell = headerRow.getCell(0);

            if (headerCell == null || !headerCell.getStringCellValue()
                                                 .equals("customer_id")) {
            }

            List<String> list = new ArrayList<>();

            for (int i = 0; i < num; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(0);
                list.add(cell.getStringCellValue() + "\n");
            }

            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
