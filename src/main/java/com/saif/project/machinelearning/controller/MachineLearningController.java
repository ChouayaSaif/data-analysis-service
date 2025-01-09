package com.saif.project.machinelearning.controller;

import com.saif.project.machinelearning.service.MachineLearningService;
import com.saif.project.storage.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MachineLearningController {

    @Autowired
    private MachineLearningService machineLearningService;

    // Endpoint to load data from the database
    @PostMapping("/load-data")
    public String loadData() {
        machineLearningService.loadDataFromDatabase();
        return "Data loaded successfully!";
    }

    // Endpoint to build the machine learning model
    @PostMapping("/build-model")
    public String buildModel() {
        try {
            machineLearningService.buildModel();
            return "Model built successfully!";
        } catch (Exception e) {
            return "Failed to build model: " + e.getMessage();
        }
    }

    // Endpoint to make a prediction
    @PostMapping("/predict")
    public String predict(@RequestBody Address address) {
        try {
            double prediction = machineLearningService.classifyInstance(address);
            return "Prediction: " + prediction;
        } catch (Exception e) {
            return "Failed to make prediction: " + e.getMessage();
        }
    }
}