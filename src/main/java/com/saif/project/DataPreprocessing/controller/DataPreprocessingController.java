package com.saif.project.DataPreprocessing.controller;

import com.saif.project.DataPreprocessing.service.DataPreprocessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class DataPreprocessingController {

    private final DataPreprocessingService dataPreprocessingService;

    @Autowired
    public DataPreprocessingController(DataPreprocessingService dataPreprocessing) {
        this.dataPreprocessingService = dataPreprocessing;
    }

    @GetMapping("/combine-ingested-data")
    public ResponseEntity<?> getCombinedIngestedData() {
        List<Map<String, Object>> combinedData = dataPreprocessingService.combineAllIngestedData();

        if (!combinedData.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("data", combinedData));
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No valid data ingested."));
        }
    }

    // Handle missing values
    @GetMapping("/handle-missing-values")
    public List<Map<String, Object>> handleMissingValues() {
        // Call the service method to handle missing values
        return dataPreprocessingService.handleMissingValues();
    }


    // Handle outliers (Z-score) for all numeric columns
    @GetMapping("/handle-outliers")
    public List<Map<String, Object>> handleOutliers() {
        // Call the handleOutliers method from the service (no need to pass columnName or threshold)
        return dataPreprocessingService.handleOutliers();
    }



}
