package com.saif.project.dataanalysis.service;


import com.saif.project.storage.model.Address;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.util.List;


// Provides basic descriptive statistics like mean, median, and standard deviation.

@Service
public class DescriptiveStatisticsService {

    public double calculateMeanLengthOfStay(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getLength_of_stay() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getLength_of_stay())));
        return stats.getMean();
    }

    public double calculateMedianLengthOfStay(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getLength_of_stay() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getLength_of_stay())));
        return stats.getPercentile(50);
    }

    // Calculate mean for the average_gross_rent field
    public double calculateMeanGrossRent(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getAverage_gross_rent() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getAverage_gross_rent())));
        return stats.getMean();
    }

    // Calculate median for the average_gross_rent field
    public double calculateMedianGrossRent(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getAverage_gross_rent() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getAverage_gross_rent())));
        return stats.getPercentile(50);
    }

    // Calculate standard deviation for the average_dwelling_value field
    public double calculateStandardDeviationDwellingValue(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getAverage_dwelling_value() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getAverage_dwelling_value())));
        return stats.getStandardDeviation();
    }

    // Calculate range for the average_dwelling_value field
    //The range is calculated as the difference between the maximum value (stats.getMax()) and the minimum value (stats.getMin())
    public double calculateRangeDwellingValue(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getAverage_dwelling_value() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getAverage_dwelling_value())));
        return stats.getMax() - stats.getMin();
    }

    // Provide summary statistics for pre_operative_days
    public String getSummaryStatisticsPreOperativeDays(List<Address> addresses) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        addresses.stream()
                .filter(a -> a.getPre_operative_days() != null)
                .forEach(a -> stats.addValue(Double.parseDouble(a.getPre_operative_days())));

        return String.format(
                "Min: %.2f, Max: %.2f, Mean: %.2f, Median: %.2f, Std Dev: %.2f",
                stats.getMin(),
                stats.getMax(),
                stats.getMean(),
                stats.getPercentile(50),
                stats.getStandardDeviation()
        );
    }

}
