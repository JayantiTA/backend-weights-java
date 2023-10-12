package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;

@EnableCaching
@EntityScan(basePackages = "com.example.backend.dao.entity")
@EnableJpaRepositories(basePackages = "com.example.backend.dao.repository")
@SpringBootApplication(scanBasePackages = { "com.example.backend" })
public class BackendApplication {

	public static void main(String[] args) {
		loadConfig();
		SpringApplication.run(BackendApplication.class, args);
	}

	private static void loadConfig() {
		Dotenv dotenv = Dotenv.configure().load();

		// Retrieve values from .env and set them as system properties
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("RDS_HOST", dotenv.get("RDS_HOST"));
		System.setProperty("RDS_PASSWORD", dotenv.get("RDS_PASSWORD"));
	}

}
