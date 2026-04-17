package com.tus.guitarorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Lab 7

import com.tus.guitarorders.dto.OrdersContactInfoDto;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Lab 7
@EnableConfigurationProperties(value = { OrdersContactInfoDto.class }) // Lab 11 - Enable configuration properties
public class GuitarordersApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuitarordersApplication.class, args);
	}
}
