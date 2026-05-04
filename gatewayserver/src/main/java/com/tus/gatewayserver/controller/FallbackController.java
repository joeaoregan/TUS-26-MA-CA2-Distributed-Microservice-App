package com.tus.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

	/**
	 * Fallback method for handling errors when the downstream service is
	 * unavailable or returns an error. This method will be called by the gateway
	 * when it detects a failure in the downstream service, allowing us to return a
	 * user-friendly message instead of an error response.
	 * 
	 * @return a Mono containing the fallback message
	 */
	@RequestMapping("/contactSupport")
	public Mono<String> contactSupport() {
		return Mono.just("An error occurred. Please try later or contact support!");
	}
}