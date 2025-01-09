package com.saif.project.DataIngestion.ingestionType;

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
}