package com.saif.project.DataIngestion.ingestionType;

import com.saif.project.DataIngestion.DataIngestionInterface;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ExcelDataIngestion implements DataIngestionInterface {
    @Override
    public Object importData(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    rowData.put(headerRow.getCell(j).getStringCellValue(), row.getCell(j).toString());
                }
                data.add(rowData);
            }
        }
        return data;
    }
}
