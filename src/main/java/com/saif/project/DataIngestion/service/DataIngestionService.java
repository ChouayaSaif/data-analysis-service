package com.saif.project.DataIngestion.service;

import com.saif.project.DataIngestion.DataIngestionInterface;
import com.saif.project.DataIngestion.ingestionType.CSVDataIngestion;
import com.saif.project.DataIngestion.ingestionType.ExcelDataIngestion;
import com.saif.project.DataIngestion.ingestionType.JSONDataIngestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DataIngestionService {

    private final Map<String, DataIngestionInterface> ingestionStrategies;
    private final Map<String, Object> ingestedDataMap;

    @Autowired
    public DataIngestionService(CSVDataIngestion csvIngestion,
                                JSONDataIngestion jsonIngestion,
                                ExcelDataIngestion excelIngestion) {
        ingestionStrategies = new HashMap<>();
        ingestionStrategies.put("csv", csvIngestion);
        ingestionStrategies.put("json", jsonIngestion);
        ingestionStrategies.put("xls", excelIngestion);
        ingestionStrategies.put("xlsx", excelIngestion);

        ingestedDataMap = new HashMap<>();
    }

    /**
     * Imports data from a file using the appropriate ingestion strategy.
     */
    public Object importData(String filePath) throws IOException {
        String fileExtension = getFileExtension(filePath);
        DataIngestionInterface ingestionStrategy = ingestionStrategies.get(fileExtension);

        if (ingestionStrategy == null) {
            throw new UnsupportedOperationException("Unsupported file type: " + fileExtension);
        }

        Object ingestedData = ingestionStrategy.importData(filePath);
        storeIngestedData(filePath, ingestedData);
        return ingestedData;
    }

    /**
     * Imports data from predefined paths for all available ingestion types.
     */
    public void importAllAvailableData() throws IOException {
        // Predefined file paths for demonstration purposes
        List<String> predefinedPaths = List.of(
                "data.csv",  // CSV file
                "data.json", // JSON file
                "data.xlsx"  // Excel file (modern format)
        );

        for (String path : predefinedPaths) {
            try {
                importData(path);
            } catch (IOException | UnsupportedOperationException e) {
                // Log or handle errors for specific files without stopping the process
                System.err.println("Failed to ingest file: " + path + " Error: " + e.getMessage());
            }
        }
    }

    public void storeIngestedData(String filePath, Object data) {
        ingestedDataMap.put(filePath, data);
    }

    public Map<String, Object> getAllIngestedData() {
        return new HashMap<>(ingestedDataMap);
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("File path does not contain an extension: " + filePath);
        }
        return filePath.substring(lastDotIndex + 1).toLowerCase();
    }
}
