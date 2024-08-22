package org.alexandresavaris.nulldb.dao;

import org.alexandresavaris.nulldb.pojo.Entry;

import java.io.IOException;

// Operation definitions for Entry instances.
public interface EntryDao {

    void put(Entry entry) throws IOException;
    Entry get(byte[] key) throws IOException;
}
