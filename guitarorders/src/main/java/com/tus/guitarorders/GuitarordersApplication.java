package com.tus.guitarorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties; // Lab 12
import org.springframework.cloud.openfeign.EnableFeignClients; // Lab 24
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Lab 7

import com.tus.guitarorders.dto.OrdersContactInfoDto; // Lab 12

@SpringBootApplication
@EnableFeignClients // Lab 24
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Lab 7
@EnableConfigurationProperties(value = { OrdersContactInfoDto.class }) // Lab 11 - Enable configuration properties
public class GuitarordersApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuitarordersApplication.class, args);
	}
}
