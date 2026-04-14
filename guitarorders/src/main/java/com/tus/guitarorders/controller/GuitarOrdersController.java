package com.tus.guitarorders.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuitarOrdersController {
	@GetMapping("/sayHello")
	public String sayHello() {
		return "Hello World";
	}
}
