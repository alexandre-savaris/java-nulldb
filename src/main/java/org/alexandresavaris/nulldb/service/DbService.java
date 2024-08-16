package org.alexandresavaris.nulldb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alexandresavaris.nulldb.pojo.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class DbService {

    // Create the database path + file.
    public static Path createDatabase(String strDbPath, String strFileName)
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

    // Put a new entry into the database file.
    public static void put(Path dbFilePath, byte[] key, byte[] value)
            throws IOException {

        // Create a new JSON entry.
        Entry entry = new Entry(key, value);
        ObjectMapper mapper = new ObjectMapper();
        String jsonEntry = mapper.writeValueAsString(entry);

        // Append the entry to the database file.
        Files.write(
            dbFilePath,
            (jsonEntry + "\n").getBytes(),
            StandardOpenOption.APPEND
        );
    }

    // Get an entry from the database file.
    public static Entry get(Path dbFilePath, byte[] key)
        throws IOException {
        // For entries deserialization.
        ObjectMapper mapper = new ObjectMapper();
        // For returning.
        Entry entryToBeReturned = null;

        // Return the last entry whose key == key argument.
        Scanner scanner = new Scanner(dbFilePath);
        while (scanner.hasNextLine()) {
            String jsonEntry = scanner.nextLine();
            Entry entry = mapper.readValue(jsonEntry, Entry.class);
            if (Arrays.equals(entry.getKey(), key)) {
                entryToBeReturned = entry;
            }
        }

        return entryToBeReturned;
    }
}
