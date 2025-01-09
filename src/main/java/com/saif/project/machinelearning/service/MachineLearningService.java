package com.saif.project.machinelearning.service;

import com.saif.project.storage.model.Address;
import com.saif.project.storage.repository.MysqlRepository;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.trees.REPTree;  // Example regression tree
import weka.core.Instance;
import weka.core.Instances;
import weka.core.DenseInstance;
import weka.core.Attribute;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MachineLearningService {

    @Autowired
    private MysqlRepository mysqlRepository;

    private Instances dataset;
    private Classifier classifier;

    public MachineLearningService() {
        // Initialize the classifier (e.g., REPTree for regression)
        classifier = new REPTree();
    }

    public void loadDataFromDatabase() {
        // Fetch all Address data from the database
        List<Address> addresses = mysqlRepository.findAll();

        // Define attributes for the Weka dataset
        ArrayList<Attribute> attributes = new ArrayList<>();

        // Numeric attributes
        attributes.add(new Attribute("latitude")); // Numeric
        attributes.add(new Attribute("longitude")); // Numeric
        attributes.add(new Attribute("average_dwelling_value")); // Numeric
        attributes.add(new Attribute("average_gross_rent")); // Numeric
        attributes.add(new Attribute("pre_operative_days")); // Numeric
        attributes.add(new Attribute("length_of_stay")); // Numeric (class attribute)

        // Collect unique values for nominal attributes
        Set<String> principalDiagnosisValues = new HashSet<>();
        Set<String> dischargeDateValues = new HashSet<>();
        Set<String> physicianNameValues = new HashSet<>();
        Set<String> mainServiceValues = new HashSet<>();
        Set<String> dayInWeekValues = new HashSet<>();
        Set<String> institutionValues = new HashSet<>();
        Set<String> departmentValues = new HashSet<>();
        Set<String> encounterNumberValues = new HashSet<>();
        Set<String> isWorkdayValues = new HashSet<>();
        Set<String> periodValues = new HashSet<>();
        Set<String> serviceDescriptionValues = new HashSet<>();
        Set<String> principalPhysicianValues = new HashSet<>();
        Set<String> isHolidayValues = new HashSet<>();
        Set<String> areaNameValues = new HashSet<>();
        Set<String> encounterMonthValues = new HashSet<>();
        Set<String> diagnosisShortDescriptionValues = new HashSet<>();
        Set<String> holidayNameValues = new HashSet<>();
        Set<String> principleProcedureValues = new HashSet<>();
        Set<String> procedureDescriptionValues = new HashSet<>();
        Set<String> fsaValues = new HashSet<>();

        for (Address address : addresses) {
            principalDiagnosisValues.add(address.getPrincipal_diagnosis());
            dischargeDateValues.add(address.getDischarge_date());
            physicianNameValues.add(address.getPhysician_name());
            mainServiceValues.add(address.getMain_service());
            dayInWeekValues.add(address.getDay_in_week());
            institutionValues.add(address.getInstitution());
            departmentValues.add(address.getDepartment());
            encounterNumberValues.add(address.getEncounter_number());
            isWorkdayValues.add(address.getIsworkday());
            periodValues.add(address.getPeriod());
            serviceDescriptionValues.add(address.getService_description());
            principalPhysicianValues.add(address.getPrincipal_physician());
            isHolidayValues.add(address.getIsholiday());
            areaNameValues.add(address.getAreaname());
            encounterMonthValues.add(address.getEncounter_month());
            diagnosisShortDescriptionValues.add(address.getDiagnosis_short_description());
            holidayNameValues.add(address.getHolidayname());
            principleProcedureValues.add(address.getPrinciple_procedure());
            procedureDescriptionValues.add(address.getProcedure_description());
            fsaValues.add(address.getFsa());
        }

        // Add "UNKNOWN" as a default value for all nominal attributes
        principalDiagnosisValues.add("UNKNOWN");
        dischargeDateValues.add("UNKNOWN");
        physicianNameValues.add("UNKNOWN");
        mainServiceValues.add("UNKNOWN");
        dayInWeekValues.add("UNKNOWN");
        institutionValues.add("UNKNOWN");
        departmentValues.add("UNKNOWN");
        encounterNumberValues.add("UNKNOWN");
        isWorkdayValues.add("UNKNOWN");
        periodValues.add("UNKNOWN");
        serviceDescriptionValues.add("UNKNOWN");
        principalPhysicianValues.add("UNKNOWN");
        isHolidayValues.add("UNKNOWN");
        areaNameValues.add("UNKNOWN");
        encounterMonthValues.add("UNKNOWN");
        diagnosisShortDescriptionValues.add("UNKNOWN");
        holidayNameValues.add("UNKNOWN");
        principleProcedureValues.add("UNKNOWN");
        procedureDescriptionValues.add("UNKNOWN");
        fsaValues.add("UNKNOWN");

        // Nominal attributes
        attributes.add(new Attribute("principal_diagnosis", new ArrayList<>(principalDiagnosisValues)));
        attributes.add(new Attribute("discharge_date", new ArrayList<>(dischargeDateValues)));
        attributes.add(new Attribute("physician_name", new ArrayList<>(physicianNameValues)));
        attributes.add(new Attribute("main_service", new ArrayList<>(mainServiceValues)));
        attributes.add(new Attribute("day_in_week", new ArrayList<>(dayInWeekValues)));
        attributes.add(new Attribute("institution", new ArrayList<>(institutionValues)));
        attributes.add(new Attribute("department", new ArrayList<>(departmentValues)));
        attributes.add(new Attribute("encounter_number", new ArrayList<>(encounterNumberValues)));
        attributes.add(new Attribute("isworkday", new ArrayList<>(isWorkdayValues)));
        attributes.add(new Attribute("period", new ArrayList<>(periodValues)));
        attributes.add(new Attribute("service_description", new ArrayList<>(serviceDescriptionValues)));
        attributes.add(new Attribute("principal_physician", new ArrayList<>(principalPhysicianValues)));
        attributes.add(new Attribute("isholiday", new ArrayList<>(isHolidayValues)));
        attributes.add(new Attribute("areaname", new ArrayList<>(areaNameValues)));
        attributes.add(new Attribute("encounter_month", new ArrayList<>(encounterMonthValues)));
        attributes.add(new Attribute("diagnosis_short_description", new ArrayList<>(diagnosisShortDescriptionValues)));
        attributes.add(new Attribute("holidayname", new ArrayList<>(holidayNameValues)));
        attributes.add(new Attribute("principle_procedure", new ArrayList<>(principleProcedureValues)));
        attributes.add(new Attribute("procedure_description", new ArrayList<>(procedureDescriptionValues)));
        attributes.add(new Attribute("fsa", new ArrayList<>(fsaValues)));

        // Create the Weka dataset
        dataset = new Instances("AddressData", attributes, addresses.size());
        dataset.setClassIndex(dataset.numAttributes() - 1); // Set length_of_stay as the class attribute

        // Convert each Address object to a Weka Instance
        for (Address address : addresses) {
            Instance instance = new DenseInstance(dataset.numAttributes());

            // Set numeric attributes
            instance.setValue(dataset.attribute("latitude"), Double.parseDouble(address.getLatitude()));
            instance.setValue(dataset.attribute("longitude"), Double.parseDouble(address.getLongitude()));
            instance.setValue(dataset.attribute("average_dwelling_value"), Double.parseDouble(address.getAverage_dwelling_value()));
            instance.setValue(dataset.attribute("average_gross_rent"), Double.parseDouble(address.getAverage_gross_rent()));
            instance.setValue(dataset.attribute("pre_operative_days"), Double.parseDouble(address.getPre_operative_days()));
            instance.setValue(dataset.attribute("length_of_stay"), Double.parseDouble(address.getLength_of_stay()));

            // Set nominal attributes
            setNominalValue(instance, "principal_diagnosis", address.getPrincipal_diagnosis());
            setNominalValue(instance, "discharge_date", address.getDischarge_date());
            setNominalValue(instance, "physician_name", address.getPhysician_name());
            setNominalValue(instance, "main_service", address.getMain_service());
            setNominalValue(instance, "day_in_week", address.getDay_in_week());
            setNominalValue(instance, "institution", address.getInstitution());
            setNominalValue(instance, "department", address.getDepartment());
            setNominalValue(instance, "encounter_number", address.getEncounter_number());
            setNominalValue(instance, "isworkday", address.getIsworkday());
            setNominalValue(instance, "period", address.getPeriod());
            setNominalValue(instance, "service_description", address.getService_description());
            setNominalValue(instance, "principal_physician", address.getPrincipal_physician());
            setNominalValue(instance, "isholiday", address.getIsholiday());
            setNominalValue(instance, "areaname", address.getAreaname());
            setNominalValue(instance, "encounter_month", address.getEncounter_month());
            setNominalValue(instance, "diagnosis_short_description", address.getDiagnosis_short_description());
            setNominalValue(instance, "holidayname", address.getHolidayname());
            setNominalValue(instance, "principle_procedure", address.getPrinciple_procedure());
            setNominalValue(instance, "procedure_description", address.getProcedure_description());
            setNominalValue(instance, "fsa", address.getFsa());

            dataset.add(instance);
        }
    }

    public void buildModel() throws Exception {
        classifier.buildClassifier(dataset);
    }

    public double classifyInstance(Address address) throws Exception {
        Instance instance = new DenseInstance(dataset.numAttributes());
        instance.setDataset(dataset);

        // Set numeric attributes
        instance.setValue(dataset.attribute("latitude"), Double.parseDouble(address.getLatitude()));
        instance.setValue(dataset.attribute("longitude"), Double.parseDouble(address.getLongitude()));
        instance.setValue(dataset.attribute("average_dwelling_value"), Double.parseDouble(address.getAverage_dwelling_value()));
        instance.setValue(dataset.attribute("average_gross_rent"), Double.parseDouble(address.getAverage_gross_rent()));
        instance.setValue(dataset.attribute("pre_operative_days"), Double.parseDouble(address.getPre_operative_days()));

        // Set nominal attributes
        setNominalValue(instance, "principal_diagnosis", address.getPrincipal_diagnosis());
        setNominalValue(instance, "discharge_date", address.getDischarge_date());
        setNominalValue(instance, "physician_name", address.getPhysician_name());
        setNominalValue(instance, "main_service", address.getMain_service());
        setNominalValue(instance, "day_in_week", address.getDay_in_week());
        setNominalValue(instance, "institution", address.getInstitution());
        setNominalValue(instance, "department", address.getDepartment());
        setNominalValue(instance, "encounter_number", address.getEncounter_number());
        setNominalValue(instance, "isworkday", address.getIsworkday());
        setNominalValue(instance, "period", address.getPeriod());
        setNominalValue(instance, "service_description", address.getService_description());
        setNominalValue(instance, "principal_physician", address.getPrincipal_physician());
        setNominalValue(instance, "isholiday", address.getIsholiday());
        setNominalValue(instance, "areaname", address.getAreaname());
        setNominalValue(instance, "encounter_month", address.getEncounter_month());
        setNominalValue(instance, "diagnosis_short_description", address.getDiagnosis_short_description());
        setNominalValue(instance, "holidayname", address.getHolidayname());
        setNominalValue(instance, "principle_procedure", address.getPrinciple_procedure());
        setNominalValue(instance, "procedure_description", address.getProcedure_description());
        setNominalValue(instance, "fsa", address.getFsa());

        // Classify the instance
        return classifier.classifyInstance(instance); // Return the predicted length_of_stay as a numeric value
    }

    private void setNominalValue(Instance instance, String attributeName, String value) {
        Attribute attribute = dataset.attribute(attributeName);
        if (attribute.indexOfValue(value) == -1) {
            // Handle unknown value by replacing it with "UNKNOWN"
            value = "UNKNOWN";
        }
        instance.setValue(attribute, value);
    }
}