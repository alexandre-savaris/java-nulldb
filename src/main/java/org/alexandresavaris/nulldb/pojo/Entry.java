package org.alexandresavaris.nulldb.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;

public class Entry {
    @JsonProperty("key")
    private byte[] key;
    @JsonProperty("value")
    private byte[] value;

    public Entry() {
    }

    public Entry(byte[] key, byte[] value) {

        this.key = key;
        this.value = value;
    }

    @JsonSerialize(using=org.alexandresavaris.nulldb.util.ByteArraySerializer.class)
    public byte[] getKey() {

        return key;
    }

    @JsonSerialize(using=org.alexandresavaris.nulldb.util.ByteArraySerializer.class)
    public byte[] getValue() {

        return value;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + Arrays.toString(key) +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
