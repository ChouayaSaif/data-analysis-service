package com.saif.project.dataanalysis.controller;

import com.saif.project.dataanalysis.service.CorrelationAnalysisService;
import com.saif.project.dataanalysis.service.TimeSeriesAnalysisService;
import com.saif.project.storage.repository.MysqlRepository;
import com.saif.project.dataanalysis.service.DescriptiveStatisticsService;
import com.saif.project.storage.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataAnalysisController {

    @Autowired
    private MysqlRepository mysqlRepository;

    @Autowired
    private DescriptiveStatisticsService descriptiveStatisticsService;

    @Autowired
    private CorrelationAnalysisService correlationAnalysisService;

    // Inject the service into the controller
    @Autowired
    private TimeSeriesAnalysisService timeSeriesAnalysisService;



    @GetMapping("/length-of-stay-mean")
    public double calculateMean() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.calculateMeanLengthOfStay(addresses);
    }

    @GetMapping("/length-of-stay-median")
    public double calculateMedian() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.calculateMedianLengthOfStay(addresses);
    }

    // Calculate mean for the average_gross_rent field
    @GetMapping("/gross-rent-mean")
    public double getMeanGrossRent() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.calculateMeanGrossRent(addresses);
    }

    // Calculate median for the average_gross_rent field
    @GetMapping("/gross-rent-median")
    public double getMedianGrossRent() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.calculateMedianGrossRent(addresses);
    }

    // Calculate standard deviation for the average_dwelling_value field
    @GetMapping("/dwelling-value-std-dev")
    public double getStandardDeviationDwellingValue() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.calculateStandardDeviationDwellingValue(addresses);
    }

    // Calculate range for the average_dwelling_value field
    @GetMapping("/dwelling-value-range")
    public double getRangeDwellingValue() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.calculateRangeDwellingValue(addresses);
    }

    // Provide summary statistics for pre_operative_days
    @GetMapping("/pre-operative-days-summary")
    public String getSummaryStatisticsPreOperativeDays() {
        List<Address> addresses = mysqlRepository.findAll();
        return descriptiveStatisticsService.getSummaryStatisticsPreOperativeDays(addresses);
    }


    //controlller for correlation analysis
    @GetMapping("/calculate")
    public double calculateCorrelation(@RequestParam String fieldX,
                                       @RequestParam String fieldY) {
        List<Address> addresses = mysqlRepository.findAll(); // Fetch addresses from the database
        return correlationAnalysisService.calculateCorrelation(addresses, fieldX, fieldY);
    }


    // controller for the TimeSeriesAnalysis service
    @PostMapping("/analyze")
    public Map<String, Double> analyzeTimeSeries(@RequestBody List<Address> addresses,
                                                 @RequestParam String timeField,
                                                 @RequestParam String valueField) {
        // Trigger the time series analysis and return the results
        return timeSeriesAnalysisService.analyzeTimeSeries(addresses, timeField, valueField);
    }


}
