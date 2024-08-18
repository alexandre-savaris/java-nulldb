package org.alexandresavaris.nulldb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Load properties set for the application.
@Service
public class PropertiesLoader {
    @Value("${db.path}")
    private String dbPath;
    @Value("${db.fileName}")
    private String dbFileName;

    public String getDbPath() {

        return dbPath;
    }

    public void setDbPath(String dbPath) {

        this.dbPath = dbPath;
    }

    public String getDbFileName() {

        return dbFileName;
    }

    public void setDbFileName(String dbFileName) {

        this.dbFileName = dbFileName;
    }
}
