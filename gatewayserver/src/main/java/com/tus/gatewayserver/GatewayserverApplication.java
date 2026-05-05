package com.tus.gatewayserver;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	/**
	 * This method defines the routing configuration for the API Gateway. It uses
	 * the RouteLocatorBuilder to create routes for the guitar orders and inventory
	 * services. Each route includes a path predicate to match incoming requests,
	 * filters to rewrite the path and add a response header, and a URI to forward
	 * the request to the appropriate service. Lab 27 - Implemented API Gateway
	 * routing configuration using RouteLocatorBuilder
	 * 
	 * @param routeLocatorBuilder The builder used to create the route locator.
	 * @return A RouteLocator that defines the routing configuration for the API
	 *         Gateway.
	 */
	@Bean
	RouteLocator guitarRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p.path("/guitar/orders/**")
						.filters(f -> f.rewritePath("/guitar/orders/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								/*.circuitBreaker(config -> config.setName("ordersCircuitBreaker") // Lab 29
										.setFallbackUri("forward:/contactSupport"))*/) // Lab 30
						.uri("lb://ORDERS"))
				.route(p -> p.path("/guitar/inventory/**")
						.filters(f -> f.rewritePath("/guitar/inventory/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								/*.circuitBreaker(config -> config.setName("inventoryCircuitBreaker") // Lab 29
										.setFallbackUri("forward:/contactSupport"))*/) // Lab 30
						.uri("lb://INVENTORY"))
				.build();
	}
}
