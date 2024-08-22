package org.alexandresavaris.nulldb.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alexandresavaris.nulldb.bean.DbBean;
import org.alexandresavaris.nulldb.pojo.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

// Implementing the operations for Entry instances.
@Repository
public class EntryDaoImpl implements EntryDao {
    // For accessing the on-disk and in-memory database structures.
    @Autowired
    private DbBean dbBean;

    // Put a new entry into the database file and into the Memtable.
    @Override
    public void put(Entry entry) throws IOException {

        // Create a new JSON entry.
        ObjectMapper mapper = new ObjectMapper();
        String jsonEntry = mapper.writeValueAsString(entry);

        // Append the entry to the database file.
        Files.write(
            dbBean.getDbFilePath(),
            (jsonEntry + "\n").getBytes(),
            StandardOpenOption.APPEND
        );

        // Insert the entry into the Memtable.
        dbBean.getMemTable().put(entry.getKey(), entry.getValue());
    }

    // Get an entry from the Memtable.
    @Override
    public Entry get(byte[] key) throws IOException {
        // For entries deserialization.
        ObjectMapper mapper = new ObjectMapper();

        // Return the entry whose key == key argument.
        return (!dbBean.getMemTable().containsKey(key))
            ? null
            : new Entry(key, dbBean.getMemTable().get(key));
    }
}
