package com.saif.project.DataIngestion.ingestionType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saif.project.DataIngestion.DataIngestionInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class JSONDataIngestion implements DataIngestionInterface {
    private static final Logger logger = LoggerFactory.getLogger(JSONDataIngestion.class);

    @Override
    public Object importData(String filePath) throws IOException {
        // Resolve the file path relative to the project directory
        File file = new File(System.getProperty("user.dir") + File.separator + filePath);

        // Validate if the file exists
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("The file at path " + filePath + " does not exist.");
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON data into a Map
            return objectMapper.readValue(file, Map.class);
        } catch (IOException e) {
            logger.error("Error reading JSON file: {}", e.getMessage(), e);
            throw e;
        }
    }
}





/*package com.saif.project.DataIngestion.ingestionType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saif.project.DataIngestion.DataIngestionInterface;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class JSONDataIngestion implements DataIngestionInterface {
    @Override
    public Object importData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), Map.class);
    }
}*/