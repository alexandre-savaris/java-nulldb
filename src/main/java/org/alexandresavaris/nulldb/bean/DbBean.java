package org.alexandresavaris.nulldb.bean;

import org.alexandresavaris.nulldb.util.Utils;

import java.io.IOException;
import java.nio.file.Path;

// For managing on-disk and in-memory database structures.
public class DbBean {
    // For accessing the database file.
    private final String dbPath;
    private final String dbFileName;
    private final Path dbFilePath;

    public DbBean(String dbPath, String dbFileName) throws IOException {

        this.dbPath = dbPath;
        this.dbFileName = dbFileName;
        this.dbFilePath = Utils.getDatabasePath(dbPath, dbFileName);
    }

    public String getDbPath() {

        return dbPath;
    }

    public String getDbFileName() {

        return dbFileName;
    }

    public Path getDbFilePath() {

        return dbFilePath;
    }
}
