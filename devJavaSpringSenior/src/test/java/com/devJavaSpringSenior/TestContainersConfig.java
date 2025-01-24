package com.devJavaSpringSenior;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

import com.zaxxer.hikari.HikariDataSource;

@TestConfiguration
public class TestContainersConfig {
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public PostgreSQLContainer<?> postgreSQLContainer() {
		PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
				.withDatabaseName("contasapagarteste")
				.withUsername("postgres")
				.withPassword("root");
		return postgres;
				
	}
	
	@Bean
	@Primary
	public DataSource dataSource(PostgreSQLContainer postgres) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(postgres.getJdbcUrl());
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		return dataSource;
	}

}