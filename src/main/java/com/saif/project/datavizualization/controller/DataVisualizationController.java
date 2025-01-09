package com.saif.project.datavizualization.controller;

import com.saif.project.datavizualization.service.DataVisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataVisualizationController {

    @Autowired
    private DataVisualizationService dataVisualizationService;

    @GetMapping("/visualization-data/dwelling-values")
    public Map<String, Double> getAggregatedDwellingValues() {
        return dataVisualizationService.getAggregatedDwellingValues();
    }

    @GetMapping("/visualization-data/length-of-stay")
    public Map<String, Double> getAggregatedLengthOfStay() {
        return dataVisualizationService.getAggregatedLengthOfStay();
    }

    @GetMapping("/visualization-data/gross-rent")
    public Map<String, Double> getAggregatedGrossRent() {
        return dataVisualizationService.getAggregatedGrossRent();
    }

    @GetMapping("/visualization-data/encounters-by-day")
    public Map<String, Long> getEncountersByDayOfWeek() {
        return dataVisualizationService.getEncountersByDayOfWeek();
    }

    @GetMapping("/visualization-data/encounters-by-month")
    public Map<String, Long> getEncountersByMonth() {
        return dataVisualizationService.getEncountersByMonth();
    }

    @GetMapping("/visualization-data/average-length-of-stay-by-department")
    public Map<String, Double> getAverageLengthOfStayByDepartment() {
        return dataVisualizationService.getAverageLengthOfStayByDepartment();
    }

    @GetMapping("/visualization-data/total-gross-rent-by-institution")
    public Map<String, Double> getTotalGrossRentByInstitution() {
        return dataVisualizationService.getTotalGrossRentByInstitution();
    }

    @GetMapping("/visualization-data/encounters-by-diagnosis")
    public Map<String, Long> getEncountersByPrincipalDiagnosis() {
        return dataVisualizationService.getEncountersByPrincipalDiagnosis();
    }

    @GetMapping("/visualization-data/average-gross-rent-by-area")
    public Map<String, Double> getAverageGrossRentByArea() {
        return dataVisualizationService.getAverageGrossRentByArea();
    }

    @GetMapping("/visualization-data/encounters-by-holiday")
    public Map<String, Long> getEncountersByHoliday() {
        return dataVisualizationService.getEncountersByHoliday();
    }

    @GetMapping("/visualization-data/total-pre-operative-days-by-physician")
    public Map<String, Double> getTotalPreOperativeDaysByPhysician() {
        return dataVisualizationService.getTotalPreOperativeDaysByPhysician();
    }

    @GetMapping("/visualization-data/encounters-by-service")
    public Map<String, Long> getEncountersByServiceDescription() {
        return dataVisualizationService.getEncountersByServiceDescription();
    }

    @GetMapping("/visualization-data/average-dwelling-value-by-fsa")
    public Map<String, Double> getAverageDwellingValueByFSA() {
        return dataVisualizationService.getAverageDwellingValueByFSA();
    }

    @GetMapping("/visualization-data/encounters-by-workday-holiday")
    public Map<String, Long> getEncountersByWorkdayHoliday() {
        return dataVisualizationService.getEncountersByWorkdayHoliday();
    }
}