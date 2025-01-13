package com.saif.project.DataIngestion.controller;

import com.saif.project.DataIngestion.service.DataIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class DataIngestionController {

    private final DataIngestionService dataIngestionService;

    @Autowired
    public DataIngestionController(DataIngestionService dataIngestionService) {
        this.dataIngestionService = dataIngestionService;
    }

    // POST endpoint to ingest CSV data from the request body
    @PostMapping("/ingest")
    public Map<String, Object> importData(@RequestBody String csvData) {
        Map<String, Object> result = new HashMap<>();
        try {
            Object data = dataIngestionService.importData(csvData); // Pass CSV data to the service
            result.put("data", data);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "Failed to import data.");
        }
        return result;
    }

    @GetMapping("/get-ingested-data")
    public Map<String, Object> getIngestedData(@RequestParam String filePath) {
        Map<String, Object> result = new HashMap<>();
        Object ingestedData = dataIngestionService.getIngestedData(filePath);

        if (ingestedData != null) {
            result.put("data", ingestedData);  // Return the ingested data
        } else {
            result.put("error", "No data found for the given file path.");
        }

        return result;
    }

    // New method to get all ingested data
    @GetMapping("/get-all-ingested-data")
    public Map<String, Object> getAllIngestedData() {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> allData = dataIngestionService.getAllIngestedData();

        if (!allData.isEmpty()) {
            result.put("data", allData);  // Return all the ingested data
        } else {
            result.put("error", "No data has been ingested yet.");
        }

        return result;
    }
}
