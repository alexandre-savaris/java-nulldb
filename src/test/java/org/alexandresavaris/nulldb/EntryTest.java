package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.bean.DbBean;
import org.alexandresavaris.nulldb.dao.EntryDao;
import org.alexandresavaris.nulldb.pojo.Entry;
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
    // For accessing the on-disk and in-memory database structures.
    @Autowired
    private DbBean dbBean;
    // For executing operations on Entry instances.
    @Autowired
    private EntryDao entryDao;
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
            this.dbBean.getDbPath(),
            () -> "The property 'db.path' shall not be null!"
        );

        assertNotNull(
            this.dbBean.getDbFileName(),
            () -> "The property 'db.fileName' shall not be null!"
        );

        assertDoesNotThrow(() -> {
            this.entryDao.put(this.entry1);
        });

        assertDoesNotThrow(() -> {
            this.retrievedEntry = this.entryDao.get(this.entry1.getKey());
        });

        assertEquals(this.entry1.toString(), this.retrievedEntry.toString());

        assertDoesNotThrow(() -> {
            FileSystemUtils.deleteRecursively(
                Path.of(this.dbBean.getDbPath())
            );
        });
    }
}
