package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.dao.EntryDao;
import org.alexandresavaris.nulldb.service.PropertiesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.alexandresavaris.nulldb.util.Utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@SpringBootApplication
public class NulldbApplication {
	// For the application properties.
	@Autowired
	PropertiesLoader propertiesLoader;

	public static void main(String[] args) {

		SpringApplication.run(NulldbApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		return runner -> {
			final Path dbFilePath = Utils.getDatabasePath(
				propertiesLoader.getDbPath(),
				propertiesLoader.getDbFileName()
			);
			EntryDao.put(
				dbFilePath,
				new Entry(
					"foo".getBytes(StandardCharsets.UTF_8),
					"bar".getBytes(StandardCharsets.UTF_8)
				)
			);
			EntryDao.put(
				dbFilePath,
				new Entry(
					"baz".getBytes(StandardCharsets.UTF_8),
					"qux".getBytes(StandardCharsets.UTF_8)
				)
			);
			EntryDao.put(
				dbFilePath,
				new Entry(
					"foo".getBytes(StandardCharsets.UTF_8),
					"goo".getBytes(StandardCharsets.UTF_8)
				)
			);
			Entry entry
				= EntryDao.get(
					dbFilePath,
					"foo".getBytes(StandardCharsets.UTF_8)
				);
			System.out.println(
				"Entry: key = "
					+ new String(entry.getKey(), StandardCharsets.UTF_8)
				+ " | value = "
					+ new String(entry.getValue(), StandardCharsets.UTF_8)
			);
		};
	}
}
