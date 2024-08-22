package org.alexandresavaris.nulldb;

import org.alexandresavaris.nulldb.bean.DbBean;
import org.alexandresavaris.nulldb.pojo.Entry;
import org.alexandresavaris.nulldb.dao.EntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class NulldbApplication {
	// For accessing the on-disk and in-memory database structures.
	@Autowired
	private DbBean dbBean;

	public static void main(String[] args) {

		SpringApplication.run(NulldbApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		return runner -> {
			EntryDao.put(
				dbBean.getDbFilePath(),
				new Entry(
					"foo".getBytes(StandardCharsets.UTF_8),
					"bar".getBytes(StandardCharsets.UTF_8)
				)
			);
			EntryDao.put(
				dbBean.getDbFilePath(),
				new Entry(
					"baz".getBytes(StandardCharsets.UTF_8),
					"qux".getBytes(StandardCharsets.UTF_8)
				)
			);
			EntryDao.put(
				dbBean.getDbFilePath(),
				new Entry(
					"foo".getBytes(StandardCharsets.UTF_8),
					"goo".getBytes(StandardCharsets.UTF_8)
				)
			);
			Entry entry
				= EntryDao.get(
					dbBean.getDbFilePath(),
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
