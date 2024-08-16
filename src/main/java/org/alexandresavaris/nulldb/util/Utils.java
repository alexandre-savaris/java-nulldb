package org.alexandresavaris.nulldb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alexandresavaris.nulldb.pojo.Entry;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class Utils {

    // Load the application properties.
    public static Properties loadProperties() throws IOException {

        String rootPath
                = Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResource(""))
                .getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));

        return properties;
    }
}
