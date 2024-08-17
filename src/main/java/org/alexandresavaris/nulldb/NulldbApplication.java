package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.dao.EntryDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.alexandresavaris.nulldb.util.Utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
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
			final Path dbFilePath = Utils.getDatabasePath(
				properties.getProperty("db.path"),
				properties.getProperty("db.fileName")
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
