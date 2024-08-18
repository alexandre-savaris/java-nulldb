package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.dao.EntryDao;
import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.service.PropertiesLoader;
import org.alexandresavaris.nulldb.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.FileSystemUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class EntryTest {
    // For the application properties.
    @Autowired
    private PropertiesLoader propertiesLoader;
    // The database path for the tests.
    private Path dbFilePath = null;
    // Entries used for the tests.
    private Entry entry1
        = new Entry(
            "foo".getBytes(StandardCharsets.UTF_8),
            "bar".getBytes(StandardCharsets.UTF_8)
        );
    private Entry retrievedEntry;

    @Test
    public void putAndGetTheSameEntryFromTheDatabase() {

        assertNotNull(
            this.propertiesLoader.getDbPath(),
            () -> "The property 'db.path' shall not be null!"
        );

        assertNotNull(
            this.propertiesLoader.getDbFileName(),
            () -> "The property 'db.fileName' shall not be null!"
        );

        assertDoesNotThrow(() -> {
            this.dbFilePath
                = Utils.getDatabasePath(
                this.propertiesLoader.getDbPath(),
                this.propertiesLoader.getDbFileName()
            );
        });

        assertDoesNotThrow(() -> {
            EntryDao.put(this.dbFilePath, this.entry1);
        });

        assertDoesNotThrow(() -> {
            this.retrievedEntry
                = EntryDao.get(
                    this.dbFilePath,
                    this.entry1.getKey()
                );
        });

        assertEquals(this.entry1.toString(), this.retrievedEntry.toString());

        assertDoesNotThrow(() -> {
            FileSystemUtils.deleteRecursively(
                Path.of(this.propertiesLoader.getDbPath())
            );
        });
    }
}
