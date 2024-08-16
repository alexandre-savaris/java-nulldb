package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.service.DbService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.alexandresavaris.nulldb.util.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
public class NulldbApplication {

	public static void main(String[] args) {

		SpringApplication.run(NulldbApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		return runner -> {
			final Properties properties = Utils.loadProperties();
			final Path dbFilePath = DbService.createDatabase(
				properties.getProperty("db.path"),
				properties.getProperty("db.fileName")
			);
			DbService.put(
					dbFilePath,
					"foo".getBytes(StandardCharsets.UTF_8),
					"bar".getBytes(StandardCharsets.UTF_8)
			);
			DbService.put(
					dbFilePath,
					"baz".getBytes(StandardCharsets.UTF_8),
					"qux".getBytes(StandardCharsets.UTF_8)
			);
			DbService.put(
					dbFilePath,
					"foo".getBytes(StandardCharsets.UTF_8),
					"goo".getBytes(StandardCharsets.UTF_8)
			);
			Entry entry
				= DbService.get(
					dbFilePath,
					"foo".getBytes(StandardCharsets.UTF_8)
				);
			System.out.println("Entry: key = " + Arrays.toString(entry.getKey()) + " | value = " + Arrays.toString(entry.getValue()));
		};
	}
}
