package com.saif.project.DataIngestion.service;

import com.saif.project.DataIngestion.ingestionType.CSVDataIngestion;
import com.saif.project.DataIngestion.DataIngestionInterface;
import com.saif.project.DataIngestion.ingestionType.ExcelDataIngestion;
import com.saif.project.DataIngestion.ingestionType.JSONDataIngestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DataIngestionService {

    private final Map<String, DataIngestionInterface> ingestionStrategies;
    private final Map<String, Object> ingestedDataMap;  // Variable to store ingested data

    @Autowired
    public DataIngestionService(CSVDataIngestion csvIngestion,
                                JSONDataIngestion jsonIngestion,
                                ExcelDataIngestion excelIngestion) {
        ingestionStrategies = new HashMap<>();
        ingestionStrategies.put("csv", csvIngestion);
        ingestionStrategies.put("json", jsonIngestion);
        ingestionStrategies.put("xls", excelIngestion);
        ingestionStrategies.put("xlsx", excelIngestion);

        ingestedDataMap = new HashMap<>(); // Initialize the map to store ingested data
    }

    public Object importData(String filePath) throws IOException {
        String fileExtension = getFileExtension(filePath);
        DataIngestionInterface ingestionStrategy = ingestionStrategies.get(fileExtension);

        if (ingestionStrategy == null) {
            throw new UnsupportedOperationException("Unsupported file type: " + fileExtension);
        }

        Object ingestedData = ingestionStrategy.importData(filePath);

        // Store the ingested data in the map with the file path as the key
        ingestedDataMap.put(filePath, ingestedData);

        return ingestedData;
    }

    public Object getIngestedData(String filePath) {
        return ingestedDataMap.get(filePath);  // Get the ingested data by file path
    }

    // New method to get all ingested data
    public Map<String, Object> getAllIngestedData() {
        return ingestedDataMap;  // Return the entire map of ingested data
    }

    private String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
    }
}
