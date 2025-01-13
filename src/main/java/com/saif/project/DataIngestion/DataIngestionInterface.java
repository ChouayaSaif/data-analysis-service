package com.saif.project.DataIngestion;

import java.io.IOException;

public interface DataIngestionInterface {
    Object importData(String csvData) throws IOException; // Accept CSV data as a String
}


/*package com.saif.project.DataIngestion;

import java.io.IOException;

public interface DataIngestionInterface {
    Object importData(String filePath) throws IOException;
}
*/