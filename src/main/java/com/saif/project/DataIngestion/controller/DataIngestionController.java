package com.saif.project.DataIngestion.controller;

import com.saif.project.DataIngestion.ingestionType.CSVDataIngestion;
import com.saif.project.DataIngestion.ingestionType.ExcelDataIngestion;
import com.saif.project.DataIngestion.ingestionType.JSONDataIngestion;
import com.saif.project.DataIngestion.service.DataIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;

@RestController
public class DataIngestionController {

    private final DataIngestionService dataIngestionService;

    @Autowired
    private CSVDataIngestion csvDataIngestion;

    @Autowired
    private ExcelDataIngestion excelDataIngestion;

    @Autowired
    private JSONDataIngestion jsonDataIngestion;

    @Autowired
    public DataIngestionController(DataIngestionService dataIngestionService) {
        this.dataIngestionService = dataIngestionService;
    }

    // Predefined file path relative to the project directory
    private static final String CSV_DEFAULT_FILE_PATH = "data.csv";

    @GetMapping("/ingest-csv")
    public ResponseEntity<?> importCSVData() {
        try {
            Object data = csvDataIngestion.importData(CSV_DEFAULT_FILE_PATH);
            dataIngestionService.storeIngestedData(CSV_DEFAULT_FILE_PATH, data);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading the file: " + e.getMessage());
        }
    }

    private static final String EXCEL_DEFAULT_FILE_PATH = "data.xlsx";

    @GetMapping("/ingest-excel")
    public ResponseEntity<?> importExcelData() {
        try {
            // Use the predefined file path for Excel data
            Object data = excelDataIngestion.importData(EXCEL_DEFAULT_FILE_PATH);
            dataIngestionService.storeIngestedData(EXCEL_DEFAULT_FILE_PATH, data);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading the file: " + e.getMessage());
        }
    }

    // Predefined file path relative to the project directory
    private static final String JSON_DEFAULT_FILE_PATH = "data.json";

    @GetMapping("/ingest-json")
    public ResponseEntity<?> importJSONData() {
        try {
            // Use the predefined file path for JSON data
            Object data = jsonDataIngestion.importData(JSON_DEFAULT_FILE_PATH);
            dataIngestionService.storeIngestedData(JSON_DEFAULT_FILE_PATH, data);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading the file: " + e.getMessage());
        }
    }

    @GetMapping("/get-all-ingested-data")
    public ResponseEntity<?> getAllIngestedData() {
        try {
            // Trigger ingestion of all data types for demonstration purposes
            dataIngestionService.importAllAvailableData();

            // Retrieve all ingested data
            Map<String, Object> allData = dataIngestionService.getAllIngestedData();

            if (!allData.isEmpty()) {
                return ResponseEntity.ok(Collections.singletonMap("data", allData));
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No data has been ingested yet."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Failed to retrieve ingested data: " + e.getMessage()));
        }
    }
}