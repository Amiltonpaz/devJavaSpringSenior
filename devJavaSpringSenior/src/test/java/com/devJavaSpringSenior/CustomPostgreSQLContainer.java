package com.devJavaSpringSenior;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgreSQLContainer extends PostgreSQLContainer<CustomPostgreSQLContainer>{

	 private static final String IMAGE_VERSION = "postgres:16";
	    private static final String DATABASE_NAME = "contasapagar";

	    public CustomPostgreSQLContainer() {
	        super(IMAGE_VERSION);
	        withDatabaseName(DATABASE_NAME);
	        withUsername("seu_usuario");
	        withPassword("sua_senha");
	    }
}
