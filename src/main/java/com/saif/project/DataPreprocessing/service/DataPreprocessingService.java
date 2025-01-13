package com.saif.project.DataPreprocessing.service;

import com.saif.project.DataIngestion.service.DataIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.saif.project.storage.model.Address;
import com.saif.project.storage.repository.MysqlRepository;
import com.saif.project.DataIngestion.service.DataIngestionService;


@Service
public class DataPreprocessingService {

    @Autowired
    MysqlRepository mysqlRepository;

    private final DataIngestionService dataIngestionService;

    @Autowired
    public DataPreprocessingService(DataIngestionService dataIngestionService) {
        this.dataIngestionService = dataIngestionService;
    }



    // Method to combine all ingested data into a single list (DataFrame-like structure)
    public List<Map<String, Object>> combineAllIngestedData() {
        List<Map<String, Object>> combinedData = new ArrayList<>();

        try {
            // Retrieve all ingested data
            Map<String, Object> allData = dataIngestionService.getAllIngestedData();

            // Iterate through each data source (e.g., data.csv, data.xlsx)
            for (Map.Entry<String, Object> entry : allData.entrySet()) {
                String sourceName = entry.getKey(); // e.g., "data.csv", "data.xlsx"
                Object sourceData = entry.getValue();

                // Check if the source data is a map and contains a "data" key
                if (sourceData instanceof Map) {
                    Map<String, Object> sourceDataMap = (Map<String, Object>) sourceData;
                    Object data = sourceDataMap.get("data");

                    // Check if the "data" key contains a list of records
                    if (data instanceof List) {
                        List<Map<String, Object>> records = (List<Map<String, Object>>) data;
                        combinedData.addAll(records); // Add all records to the combined list
                    }
                }
            }
        } catch (Exception e) {
            // Log the error and return an empty list or handle it as per your requirement
            System.err.println("Failed to combine ingested data: " + e.getMessage());
        }

        return combinedData;
    }


    // Method to handle missing values by replacing them with 0
    public List<Map<String, Object>> handleMissingValues() {
        List<Map<String, Object>> combinedData = combineAllIngestedData();

        // Check data types before processing
        for (Object record : combinedData) {
            if (record instanceof Map) {
                Map<String, Object> recordMap = (Map<String, Object>) record;
                for (Map.Entry<String, Object> entry : recordMap.entrySet()) {
                    if (entry.getValue() == null) {
                        entry.setValue(0); // Replace missing values with 0
                    }
                }
            } else {
                // Handle error: the record is not a Map
                System.out.println("Invalid data type encountered: " + record.getClass().getName());
            }
        }

        return combinedData;
    }


    // Ensure the mapToAddress method converts the Map<String, Object> to an Address object

    private Address mapToAddress(Map<String, Object> data) {
        Address address = new Address();

        address.setPrincipal_diagnosis((String) data.getOrDefault("principal_diagnosis", null));
        address.setDischarge_date((String) data.getOrDefault("discharge_date", null));
        address.setPhysician_name((String) data.getOrDefault("physician_name", null));
        address.setLatitude(data.get("latitude") != null ? data.get("latitude").toString() : null);
        address.setMain_service((String) data.getOrDefault("main_service", null));
        address.setDay_in_week((String) data.getOrDefault("day_in_week", null));
        address.setInstitution((String) data.getOrDefault("institution", null));
        address.setPre_operative_days((String) data.getOrDefault("pre_operative_days", null));
        address.setAverage_dwelling_value((String) data.getOrDefault("average_dwelling_value", null));
        address.setEncounter_counter((String) data.getOrDefault("encounter_counter", null));
        address.setDepartment((String) data.getOrDefault("department", null));
        address.setLongitude(data.get("longitude") != null ? data.get("longitude").toString() : null);
        address.setEncounter_number((String) data.getOrDefault("encounter_number", null));
        address.setIsworkday((String) data.getOrDefault("isworkday", null));
        address.setPeriod((String) data.getOrDefault("period", null));
        address.setService_description((String) data.getOrDefault("service_description", null));
        address.setPrincipal_physician((String) data.getOrDefault("principal_physician", null));
        address.setIsholiday((String) data.getOrDefault("isholiday", null));
        address.setAreaname((String) data.getOrDefault("areaname", null));
        address.setEncounter_month((String) data.getOrDefault("encounter_month", null));
        address.setDiagnosis_short_description((String) data.getOrDefault("diagnosis_short_description", null));
        address.setHolidayname((String) data.getOrDefault("holidayname", null));
        address.setPrinciple_procedure((String) data.getOrDefault("principle_procedure", null));
        address.setProcedure_description((String) data.getOrDefault("procedure_description", null));
        address.setFsa((String) data.getOrDefault("fsa", null));
        address.setLength_of_stay((String) data.getOrDefault("length_of_stay", null));
        address.setAverage_gross_rent((String) data.getOrDefault("average_gross_rent", null));

        return address;
    }


    public void saveAddress(Map<String, Object> data) {
        Address address = mapToAddress(data);
        mysqlRepository.save(address);
    }



    // Method to handle outliers using Z-score
    public List<Map<String, Object>> handleOutliers() {
        // Get the combined data with missing values handled
        List<Map<String, Object>> data = handleMissingValues();

        // Get all numeric columns
        List<String> numericColumns = getNumericColumns(data);

        // Default Z-score threshold for outlier removal
        double threshold = 3.0;

        // Loop through all numeric columns and handle outliers
        for (String columnName : numericColumns) {
            data = handleOutliersForColumn(data, columnName, threshold);
        }

        // Save all valid entries to the Address model
        for (Object entry : data) {
            if (entry instanceof Map) {
                saveAddress((Map<String, Object>) entry);
            } else {
                System.err.println("Skipping invalid entry: " + entry);
            }
        }

        return data;
    }

    private List<Map<String, Object>> handleOutliersForColumn(List<Map<String, Object>> data, String columnName, double threshold) {
        // Extract values from the specified column
        List<Double> values = new ArrayList<>();
        for (Map<String, Object> record : data) {
            // Ensure the column is present and the value is numeric
            Object columnValue = record.get(columnName);
            if (columnValue instanceof Number) {
                values.add(((Number) columnValue).doubleValue());
            } else {
                // Log or handle unexpected non-numeric values (Optional)
                System.out.println("Non-numeric value found in column '" + columnName + "': " + columnValue);
            }
        }

        // If no numeric values are found, return the original data (or handle the error accordingly)
        if (values.isEmpty()) {
            System.out.println("No valid numeric values found for column '" + columnName + "'");
            return data;
        }

        // Calculate mean and standard deviation
        double mean = values.stream().mapToDouble(v -> v).average().orElse(0.0);
        double stdDev = Math.sqrt(values.stream().mapToDouble(v -> Math.pow(v - mean, 2)).average().orElse(0.0));

        // Remove outliers based on Z-score
        return data.stream()
                .filter(record -> {
                    if (record.containsKey(columnName) && record.get(columnName) instanceof Number) {
                        double value = ((Number) record.get(columnName)).doubleValue();
                        double zScore = (value - mean) / stdDev;
                        return Math.abs(zScore) <= threshold; // Keep record if Z-score is within threshold
                    }
                    return true; // Keep record if column is missing or not numeric
                })
                .collect(Collectors.toList());
    }



    private List<String> getNumericColumns(List<Map<String, Object>> data) {
        List<String> numericColumns = new ArrayList<>();

        // Check the first record to identify numeric columns (assuming all records have the same structure)
        if (!data.isEmpty()) {
            Map<String, Object> firstRecord = data.get(0);
            for (String columnName : firstRecord.keySet()) {
                Object value = firstRecord.get(columnName);
                if (value instanceof Number) {
                    numericColumns.add(columnName);
                }
            }
        }

        return numericColumns;
    }

}
