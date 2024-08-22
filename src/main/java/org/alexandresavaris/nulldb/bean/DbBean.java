package org.alexandresavaris.nulldb.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.util.ByteArrayComparator;
import org.alexandresavaris.nulldb.util.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.TreeMap;

// For managing on-disk and in-memory database structures.
public class DbBean {
    // For accessing the database file.
    private final String dbPath;
    private final String dbFileName;
    private final Path dbFilePath;
    // For accessing the Memtable.
    private final TreeMap<byte[], byte[]> memTable;

    public DbBean(String dbPath, String dbFileName) throws IOException {

        this.dbPath = dbPath;
        this.dbFileName = dbFileName;
        this.dbFilePath = Utils.getDatabasePath(dbPath, dbFileName);

        this.memTable = new TreeMap<>(new ByteArrayComparator());
        this.hydrate();
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

    public TreeMap<byte[], byte[]> getMemTable() {

        return memTable;
    }

    // Load the Memtable from the on-disk database log.
    private void hydrate() throws IOException {
        // For entries deserialization.
        ObjectMapper mapper = new ObjectMapper();

        Scanner scanner = new Scanner(this.dbFilePath);
        while (scanner.hasNextLine()) {
            String jsonEntry = scanner.nextLine();
            Entry entry = mapper.readValue(jsonEntry, Entry.class);
            this.memTable.put(entry.getKey(), entry.getValue());
        }
    }
}
