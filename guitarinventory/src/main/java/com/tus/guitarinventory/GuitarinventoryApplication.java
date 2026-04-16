package com.tus.guitarinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Lab 7

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Lab 7
public class GuitarinventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuitarinventoryApplication.class, args);
	}
}
