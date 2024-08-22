package org.alexandresavaris.nulldb.util;

import java.util.Comparator;

// For comparing two byte arrays.
// Source: https://stackoverflow.com/questions/5108091/java-comparator-for-byte-array-lexicographic
public class ByteArrayComparator implements Comparator<byte[]> {

    public int compare(byte[] left, byte[] right) {
        for (int i = 0, j = 0; i < left.length && j < right.length; i++, j++) {
            int a = (left[i] & 0xff);
            int b = (right[j] & 0xff);
            if (a != b) {
                return a - b;
            }
        }
        return left.length - right.length;
    }
}
