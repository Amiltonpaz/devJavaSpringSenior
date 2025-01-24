package com.devJavaSpringSenior;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class FlywayIntegrationTest {

	@Autowired
	private Flyway flyway;
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	void testDatabaseConnection() throws SQLException {
	    try (Connection connection = dataSource.getConnection()) {
	        assertThat(connection.isValid(2)).isTrue();
	    }
	}
	
	@Test
	void testFlywayMigrations() {
		 boolean resultMigration = flyway.migrate().success;
		 assertThat(resultMigration).isTrue();
	}
}
