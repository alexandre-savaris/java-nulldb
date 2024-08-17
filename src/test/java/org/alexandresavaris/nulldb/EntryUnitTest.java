package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.dao.EntryDao;
import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.util.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("classpath:test.properties")
public class EntryUnitTest {
    // Properties used for the tests.
    @Value("${db.path}")
    private static String dbPath;
    @Value("${db.fileName}")
    private static String dbFileName;

    // The database path for the tests.
    private static Path dbFilePath = null;
    // Entries used for the tests.
    private static Entry entry1
            = new Entry(
            "foo".getBytes(StandardCharsets.UTF_8),
            "bar".getBytes(StandardCharsets.UTF_8)
    );
    private static Entry entry2
            = new Entry(
            "baz".getBytes(StandardCharsets.UTF_8),
            "qux".getBytes(StandardCharsets.UTF_8)
    );

    @BeforeAll
    public static void executeBeforeAllTests() throws IOException {

        assertNotNull(
                dbPath,
                () -> "The property 'db.path' shall not be null!"
        );
        assertNotNull(
                dbFileName,
                () -> "The property 'db.fileName' shall not be null!"
        );

        dbFilePath = Utils.getDatabasePath(dbPath, dbFileName);
    }

    @Test
    public void putEntryIntoTheDatabase() {

        assertDoesNotThrow(() -> {
            EntryDao.put(dbFilePath, entry1);
        });
    }

    @Test
    public void getEntryFromTheDatabase() {

        assertDoesNotThrow(() -> {
            final Entry retrievedEntry = EntryDao.get(
                    dbFilePath,
                    "foo".getBytes(StandardCharsets.UTF_8)
            );
        });
    }

    @Test
    public void putAndGetTheSameEntryFromTheDatabase() throws IOException {

        EntryDao.put(dbFilePath, entry2);
        Entry retrievedEntry = EntryDao.get(dbFilePath, entry2.getKey());

        assertEquals(entry2.toString(), retrievedEntry.toString());
    }

    @AfterAll
    public static void executeAfterAllTests() throws IOException {

        FileSystemUtils.deleteRecursively(Path.of("/tmp/entry_unit_test_dir"));
    }
}
