package com.saif.project.datavizualization.service;

import com.saif.project.storage.model.Address;
import com.saif.project.storage.repository.MysqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataVisualizationService {

    @Autowired
    private MysqlRepository mysqlRepository;

    public List<Address> getAllAddresses() {
        return mysqlRepository.findAll();
    }

    // Method to aggregate average_dwelling_value by areaname
    public Map<String, Double> getAggregatedDwellingValues() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getAreaname,
                        Collectors.summingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getAverage_dwelling_value());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to aggregate length_of_stay by areaname
    public Map<String, Double> getAggregatedLengthOfStay() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getAreaname,
                        Collectors.summingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getLength_of_stay());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to aggregate average_gross_rent by areaname
    public Map<String, Double> getAggregatedGrossRent() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getAreaname,
                        Collectors.summingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getAverage_gross_rent());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to count encounters by day_in_week
    public Map<String, Long> getEncountersByDayOfWeek() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getDay_in_week,
                        Collectors.counting()
                ));
    }

    // Method to count encounters by encounter_month
    public Map<String, Long> getEncountersByMonth() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getEncounter_month,
                        Collectors.counting()
                ));
    }

    // Method to calculate average length_of_stay by department
    public Map<String, Double> getAverageLengthOfStayByDepartment() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getDepartment,
                        Collectors.averagingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getLength_of_stay());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to sum average_gross_rent by institution
    public Map<String, Double> getTotalGrossRentByInstitution() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getInstitution,
                        Collectors.summingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getAverage_gross_rent());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to count encounters by principal_diagnosis
    public Map<String, Long> getEncountersByPrincipalDiagnosis() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getPrincipal_diagnosis,
                        Collectors.counting()
                ));
    }

    // Method to calculate average gross rent by areaname
    public Map<String, Double> getAverageGrossRentByArea() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getAreaname,
                        Collectors.averagingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getAverage_gross_rent());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to count encounters by holidayname
    public Map<String, Long> getEncountersByHoliday() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getHolidayname,
                        Collectors.counting()
                ));
    }

    // Method to sum pre_operative_days by physician_name
    public Map<String, Double> getTotalPreOperativeDaysByPhysician() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getPhysician_name,
                        Collectors.summingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getPre_operative_days());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to count encounters by service_description
    public Map<String, Long> getEncountersByServiceDescription() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getService_description,
                        Collectors.counting()
                ));
    }

    // Method to calculate average dwelling value by fsa
    public Map<String, Double> getAverageDwellingValueByFSA() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        Address::getFsa,
                        Collectors.averagingDouble(address -> {
                            try {
                                return Double.parseDouble(address.getAverage_dwelling_value());
                            } catch (NumberFormatException | NullPointerException e) {
                                return 0.0;
                            }
                        })
                ));
    }

    // Method to count encounters by isworkday and isholiday
    public Map<String, Long> getEncountersByWorkdayHoliday() {
        List<Address> addresses = mysqlRepository.findAll();
        return addresses.stream()
                .collect(Collectors.groupingBy(
                        address -> address.getIsworkday() + " - " + address.getIsholiday(),
                        Collectors.counting()
                ));
    }
}