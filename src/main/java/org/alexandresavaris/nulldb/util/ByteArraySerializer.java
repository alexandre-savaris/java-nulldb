package org.alexandresavaris.nulldb.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

// Serialize arrays of bytes using their unsigned numeric representations.
// Source: https://stackoverflow.com/questions/11546917/sending-a-byte-array-in-json-using-jackson
public class ByteArraySerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(
        byte[] bytes,
        JsonGenerator jsonGenerator,
        SerializerProvider provider
    ) throws IOException {

        jsonGenerator.writeStartArray();
        for (byte b : bytes) {
            jsonGenerator.writeNumber(unsignedToBytes(b));
        }
        jsonGenerator.writeEndArray();
    }

    private static int unsignedToBytes(byte b) {

        return b & 0xFF;
    }
}
