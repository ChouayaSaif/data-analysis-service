package com.saif.project.DataIngestion.ingestionType;

import com.saif.project.DataIngestion.DataIngestionInterface;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ExcelDataIngestion implements DataIngestionInterface {
    private static final Logger logger = LoggerFactory.getLogger(ExcelDataIngestion.class);

    @Override
    public Object importData(String filePath) throws IOException {
        // Resolve the file path relative to the project directory
        File file = new File(System.getProperty("user.dir") + File.separator + filePath);

        // Validate if the file exists
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("The file at path " + filePath + " does not exist.");
        }

        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            // Extract headers from the first row
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Extract data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    logger.warn("Skipping empty row at index {}", i);
                    continue;
                }

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData.put(headers.get(j), cell.toString());
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", e.getMessage(), e);
            throw e;
        }

        // Wrap the data in a JSON-like structure
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        result.put("data", data);

        return result;
    }
}




/*package com.saif.project.DataIngestion.ingestionType;

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
*/