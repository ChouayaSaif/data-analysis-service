package com.saif.project.storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String principal_diagnosis;
    private String discharge_date;
    private String physician_name;
    private Double latitude; // Changed to Double
    private String main_service;
    private String day_in_week;
    private String institution;
    private Integer pre_operative_days; // Changed to Integer
    private Double average_dwelling_value; // Changed to Double
    private Integer encounter_counter; // Changed to Integer
    private String department;
    private Double longitude; // Changed to Double
    private Integer encounter_number; // Changed to Integer
    private String isworkday;
    private String period;
    private String service_description;
    private String principal_physician;
    private String isholiday;
    private String areaname;
    private String encounter_month;
    private String diagnosis_short_description;
    private String holidayname;
    private String principle_procedure;
    private String procedure_description;
    private String fsa;
    private Integer length_of_stay; // Changed to Integer
    private Double average_gross_rent; // Changed to Double

    // Default constructor
    public Address() {
    }

    // Getters
    public Integer getId() {
        return Id;
    }

    public String getPrincipal_diagnosis() {
        return principal_diagnosis;
    }

    public String getDischarge_date() {
        return discharge_date;
    }

    public String getPhysician_name() {
        return physician_name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getMain_service() {
        return main_service;
    }

    public String getDay_in_week() {
        return day_in_week;
    }

    public String getInstitution() {
        return institution;
    }

    public Integer getPre_operative_days() {
        return pre_operative_days;
    }

    public Double getAverage_dwelling_value() {
        return average_dwelling_value;
    }

    public Integer getEncounter_counter() {
        return encounter_counter;
    }

    public String getDepartment() {
        return department;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getEncounter_number() {
        return encounter_number;
    }

    public String getIsworkday() {
        return isworkday;
    }

    public String getPeriod() {
        return period;
    }

    public String getService_description() {
        return service_description;
    }

    public String getPrincipal_physician() {
        return principal_physician;
    }

    public String getIsholiday() {
        return isholiday;
    }

    public String getAreaname() {
        return areaname;
    }

    public String getEncounter_month() {
        return encounter_month;
    }

    public String getDiagnosis_short_description() {
        return diagnosis_short_description;
    }

    public String getHolidayname() {
        return holidayname;
    }

    public String getPrinciple_procedure() {
        return principle_procedure;
    }

    public String getProcedure_description() {
        return procedure_description;
    }

    public String getFsa() {
        return fsa;
    }

    public Integer getLength_of_stay() {
        return length_of_stay;
    }

    public Double getAverage_gross_rent() {
        return average_gross_rent;
    }

    // Setters
    public void setId(Integer id) {
        this.Id = id;
    }

    public void setPrincipal_diagnosis(String principal_diagnosis) {
        this.principal_diagnosis = principal_diagnosis;
    }

    public void setDischarge_date(String discharge_date) {
        this.discharge_date = discharge_date;
    }

    public void setPhysician_name(String physician_name) {
        this.physician_name = physician_name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setMain_service(String main_service) {
        this.main_service = main_service;
    }

    public void setDay_in_week(String day_in_week) {
        this.day_in_week = day_in_week;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setPre_operative_days(Integer pre_operative_days) {
        this.pre_operative_days = pre_operative_days;
    }

    public void setAverage_dwelling_value(Double average_dwelling_value) {
        this.average_dwelling_value = average_dwelling_value;
    }

    public void setEncounter_counter(Integer encounter_counter) {
        this.encounter_counter = encounter_counter;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setEncounter_number(Integer encounter_number) {
        this.encounter_number = encounter_number;
    }

    public void setIsworkday(String isworkday) {
        this.isworkday = isworkday;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public void setPrincipal_physician(String principal_physician) {
        this.principal_physician = principal_physician;
    }

    public void setIsholiday(String isholiday) {
        this.isholiday = isholiday;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public void setEncounter_month(String encounter_month) {
        this.encounter_month = encounter_month;
    }

    public void setDiagnosis_short_description(String diagnosis_short_description) {
        this.diagnosis_short_description = diagnosis_short_description;
    }

    public void setHolidayname(String holidayname) {
        this.holidayname = holidayname;
    }

    public void setPrinciple_procedure(String principle_procedure) {
        this.principle_procedure = principle_procedure;
    }

    public void setProcedure_description(String procedure_description) {
        this.procedure_description = procedure_description;
    }

    public void setFsa(String fsa) {
        this.fsa = fsa;
    }

    public void setLength_of_stay(Integer length_of_stay) {
        this.length_of_stay = length_of_stay;
    }

    public void setAverage_gross_rent(Double average_gross_rent) {
        this.average_gross_rent = average_gross_rent;
    }

    @Override
    public String toString() {
        return "Address{" +
                "Id=" + Id +
                ", principal_diagnosis='" + principal_diagnosis + '\'' +
                ", discharge_date='" + discharge_date + '\'' +
                ", physician_name='" + physician_name + '\'' +
                ", latitude=" + latitude +
                ", main_service='" + main_service + '\'' +
                ", day_in_week='" + day_in_week + '\'' +
                ", institution='" + institution + '\'' +
                ", pre_operative_days=" + pre_operative_days +
                ", average_dwelling_value=" + average_dwelling_value +
                ", encounter_counter=" + encounter_counter +
                ", department='" + department + '\'' +
                ", longitude=" + longitude +
                ", encounter_number=" + encounter_number +
                ", isworkday='" + isworkday + '\'' +
                ", period='" + period + '\'' +
                ", service_description='" + service_description + '\'' +
                ", principal_physician='" + principal_physician + '\'' +
                ", isholiday='" + isholiday + '\'' +
                ", areaname='" + areaname + '\'' +
                ", encounter_month='" + encounter_month + '\'' +
                ", diagnosis_short_description='" + diagnosis_short_description + '\'' +
                ", holidayname='" + holidayname + '\'' +
                ", principle_procedure='" + principle_procedure + '\'' +
                ", procedure_description='" + procedure_description + '\'' +
                ", fsa='" + fsa + '\'' +
                ", length_of_stay=" + length_of_stay +
                ", average_gross_rent=" + average_gross_rent +
                '}';
    }
}

/*public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String principal_diagnosis;
    private String discharge_date;
    private String physician_name;
    private String latitude;
    private String main_service;
    private String day_in_week;
    private String institution;
    private String pre_operative_days;
    private String average_dwelling_value;
    private String encounter_counter;
    private String department;
    private String longitude;
    private String encounter_number;
    private String isworkday;
    private String period;
    private String service_description;
    private String principal_physician;
    private String isholiday;
    private String areaname;
    private String encounter_month;
    private String diagnosis_short_description;
    private String holidayname;
    private String principle_procedure;
    private String procedure_description;
    private String fsa;
    private String length_of_stay;
    private String average_gross_rent;
*/