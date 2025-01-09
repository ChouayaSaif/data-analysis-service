package com.saif.project.storage.controller;


import com.saif.project.storage.model.Address;
import com.saif.project.storage.repository.MysqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api") // Adds a base path to all endpoints in this controller
public class DataController {

    @Autowired
    MysqlRepository mysqlRepository;

    @GetMapping("/data")
    public List<Address> getData() {
        return mysqlRepository.findAll();
    }

    // New endpoint to delete all records from the Address table
    @GetMapping("/deleteAllData")
    public String deleteAllData() {
        mysqlRepository.deleteAll();  // Deletes all entries from the Address table
        return "All data has been deleted.";
    }

    // Returns the number of rows in the Address table
    @GetMapping("/countData")
    public long countData() {
        return mysqlRepository.count();  // Returns the number of rows in the Address table
    }
}

