package com.saif.project.dataanalysis.service;

import com.saif.project.storage.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorrelationAnalysisService {

    /**
     * Calculate the Pearson correlation coefficient between two fields.
     *
     * @param addresses List of Address objects
     * @param fieldX    First field (e.g., "average_dwelling_value")
     * @param fieldY    Second field (e.g., "average_gross_rent")
     * @return Correlation coefficient between the two fields
     */
    public double calculateCorrelation(List<Address> addresses, String fieldX, String fieldY) {
        // Filter and parse the data for the two fields
        List<Double[]> dataPairs = addresses.stream()
                .map(a -> {
                    Double x = parseField(a, fieldX);
                    Double y = parseField(a, fieldY);
                    return (x != null && y != null) ? new Double[]{x, y} : null;
                })
                .filter(pair -> pair != null) // Remove null pairs
                .collect(Collectors.toList());

        if (dataPairs.isEmpty()) {
            throw new IllegalArgumentException("No valid data pairs found for correlation calculation.");
        }

        // Separate into two arrays for computation
        double[] xValues = dataPairs.stream().mapToDouble(pair -> pair[0]).toArray();
        double[] yValues = dataPairs.stream().mapToDouble(pair -> pair[1]).toArray();

        // Compute means
        double meanX = mean(xValues);
        double meanY = mean(yValues);

        // Compute covariance and standard deviations
        double covariance = 0.0;
        double varianceX = 0.0;
        double varianceY = 0.0;

        for (int i = 0; i < xValues.length; i++) {
            double diffX = xValues[i] - meanX;
            double diffY = yValues[i] - meanY;

            covariance += diffX * diffY;
            varianceX += diffX * diffX;
            varianceY += diffY * diffY;
        }

        covariance /= xValues.length;
        varianceX = Math.sqrt(varianceX / xValues.length);
        varianceY = Math.sqrt(varianceY / yValues.length);

        // Compute Pearson correlation coefficient
        return covariance / (varianceX * varianceY);
    }

    /**
     * Parse a numeric field from the Address object by name.
     *
     * @param address Address object
     * @param fieldName Field name as a string
     * @return Parsed double value or null if not valid
     */
    private Double parseField(Address address, String fieldName) {
        try {
            switch (fieldName) {
                case "average_dwelling_value":
                    return address.getAverage_dwelling_value() != null ?
                            Double.parseDouble(address.getAverage_dwelling_value()) : null;
                case "average_gross_rent":
                    return address.getAverage_gross_rent() != null ?
                            Double.parseDouble(address.getAverage_gross_rent()) : null;
                case "pre_operative_days":
                    return address.getPre_operative_days() != null ?
                            Double.parseDouble(address.getPre_operative_days()) : null;
                case "length_of_stay":
                    return address.getLength_of_stay() != null ?
                            Double.parseDouble(address.getLength_of_stay()) : null;
                default:
                    throw new IllegalArgumentException("Field name not supported for correlation analysis.");
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Calculate the mean of an array of doubles.
     *
     * @param values Array of double values
     * @return Mean of the values
     */
    private double mean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
}
