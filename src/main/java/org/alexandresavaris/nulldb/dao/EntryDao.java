package org.alexandresavaris.nulldb.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alexandresavaris.nulldb.pojo.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

// Implementing the operations for Entry instances.
public class EntryDao {

    // Put a new entry into the database file.
    public static void put(Path dbFilePath, Entry entry) throws IOException {

        // Create a new JSON entry.
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
    public static Entry get(Path dbFilePath, byte[] key) throws IOException {
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
