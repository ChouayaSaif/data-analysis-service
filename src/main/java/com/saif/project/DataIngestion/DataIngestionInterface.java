package com.saif.project.DataIngestion;

import java.io.IOException;

public interface DataIngestionInterface {
    Object importData(String filePath) throws IOException;
}
