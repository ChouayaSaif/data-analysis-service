package com.saif.project.dataanalysis.service;

import com.saif.project.storage.model.Address;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeSeriesAnalysisService {

    // Analyze time series by aggregating values for each time period (e.g., month, year)
    public Map<String, Double> analyzeTimeSeries(List<Address> addresses, String timeField, String valueField) {
        if (addresses == null || addresses.isEmpty()) {
            System.out.println("No data available for time series analysis.");
            return Collections.emptyMap();
        }

        // Define the date format based on how the date is stored (assuming "yyyy-MM-dd")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the address data and organize by time (e.g., month-year)
        Map<String, List<Double>> timeSeriesData = new HashMap<>();

        for (Address address : addresses) {
            String timeValue = getTimeFieldValue(address, timeField);
            double value = parseValueField(address, valueField);

            if (timeValue != null && !Double.isNaN(value)) {
                // Group by month and year (assuming timeField is 'discharge_date')
                LocalDate date = LocalDate.parse(timeValue, formatter);
                String timePeriod = date.getYear() + "-" + (date.getMonthValue()); // Group by year-month

                timeSeriesData.putIfAbsent(timePeriod, new ArrayList<>());
                timeSeriesData.get(timePeriod).add(value);
            }
        }

        // Calculate the average for each time period
        Map<String, Double> timeSeriesAverages = timeSeriesData.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0)
                ));

        return timeSeriesAverages;
    }

    // Extracts the time field value (like 'discharge_date') from the address
    private String getTimeFieldValue(Address address, String timeField) {
        switch (timeField) {
            case "discharge_date":
                return address.getDischarge_date();
            default:
                System.out.println("Unsupported time field: " + timeField);
                return null;
        }
    }

    // Parses the value field from the address (e.g., 'encounter_counter', 'length_of_stay')
    private double parseValueField(Address address, String valueField) {
        try {
            switch (valueField) {
                case "encounter_counter":
                    return address.getEncounter_counter() != null ? address.getEncounter_counter().doubleValue() : Double.NaN;
                case "length_of_stay":
                    return address.getLength_of_stay() != null ? address.getLength_of_stay().doubleValue() : Double.NaN;
                // Add more cases for other value fields as needed
                default:
                    System.out.println("Unsupported value field: " + valueField);
                    return Double.NaN;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for field: " + valueField);
            return Double.NaN;
        }
    }
}