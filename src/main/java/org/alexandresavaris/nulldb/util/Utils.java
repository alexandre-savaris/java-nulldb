package org.alexandresavaris.nulldb.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Utility methods for the application.
public class Utils {

    // Get the database path based on path + file name.
    // If it doesn't exist, create it.
    public static Path getDatabasePath(String strDbPath, String strFileName)
        throws IOException {

        // Verify the existence/create the database path.
        Path dbPath = Path.of(strDbPath);
        if (Files.notExists(dbPath)) {
            Files.createDirectory(dbPath);
        }

        // Verify the existence/create the database file.
        Path dbFilePath = Paths.get(strDbPath, strFileName);
        if (Files.notExists(dbFilePath)) {
            Files.createFile(dbFilePath);
        }

        return dbFilePath;
    }
}
