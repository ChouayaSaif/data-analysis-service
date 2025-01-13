




package com.saif.project.DataIngestion.ingestionType;

import com.saif.project.DataIngestion.DataIngestionInterface;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CSVDataIngestion implements DataIngestionInterface {
    @Override
    public Object importData(String filePath) throws IOException {
        List<String> data = new ArrayList<>();

        // Get the project directory path
        String projectDirectory = System.getProperty("user.dir");
        String fullPath = projectDirectory + File.separator + filePath;

        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
    }
}