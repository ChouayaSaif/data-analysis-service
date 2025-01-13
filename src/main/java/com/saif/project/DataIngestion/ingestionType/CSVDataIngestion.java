package com.saif.project.DataIngestion.ingestionType;

import com.saif.project.DataIngestion.DataIngestionInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CSVDataIngestion implements DataIngestionInterface {
    private static final Logger logger = LoggerFactory.getLogger(CSVDataIngestion.class);

    @Override
    public Object importData(String filePath) throws IOException {
        File file = new File(System.getProperty("user.dir") + File.separator + filePath);

        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("The file at path " + filePath + " does not exist.");
        }

        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> headers = new ArrayList<>();
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    headers = Arrays.asList(values);
                    isHeader = false;
                } else {
                    if (values.length != headers.size()) {
                        logger.warn("Skipping malformed row: expected {} columns, found {}", headers.size(), values.length);
                        continue;
                    }
                    Map<String, String> row = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        row.put(headers.get(i), values[i]);
                    }
                    data.add(row);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage(), e);
            throw e;
        }

        Map<String, List<Map<String, String>>> result = new HashMap<>();
        result.put("data", data);
        return result;
    }
}













/*package com.saif.project.DataIngestion.ingestionType;

import com.saif.project.DataIngestion.DataIngestionInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CSVDataIngestion implements DataIngestionInterface {
    private static final Logger logger = LoggerFactory.getLogger(CSVDataIngestion.class);

    @Override
    public Object importData(String filePath) throws IOException {
        // Resolve the file path relative to the project directory
        File file = new File(System.getProperty("user.dir") + File.separator + filePath);

        // Validate if the file exists
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("The file at path " + filePath + " does not exist.");
        }

        // Parse the CSV file
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> headers = new ArrayList<>();
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    // First line is the header
                    headers = Arrays.asList(values);
                    isHeader = false;
                } else {
                    // Subsequent lines are data rows
                    if (values.length != headers.size()) {
                        logger.warn("Skipping malformed row: expected {} columns, found {}", headers.size(), values.length);
                        continue; // Skip rows with incorrect column count
                    }

                    Map<String, String> row = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        row.put(headers.get(i), values[i]);
                    }
                    data.add(row);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage(), e);
            throw e;
        }

        // Wrap the data in the desired JSON structure
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        result.put("data", data);

        return result;
    }
}*/




/*package com.saif.project.DataIngestion.ingestionType;

import com.saif.project.DataIngestion.DataIngestionInterface;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CSVDataIngestion implements DataIngestionInterface {
    @Override
    public Object importData(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
    }
}*/