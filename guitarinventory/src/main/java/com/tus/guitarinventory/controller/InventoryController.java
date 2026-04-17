package com.tus.guitarinventory.controller;

import com.tus.guitarinventory.constants.InventoryConstants;
import com.tus.guitarinventory.dto.InventoryContactInfoDto;
import com.tus.guitarinventory.dto.InventoryDto;
import com.tus.guitarinventory.dto.ResponseDto;
import com.tus.guitarinventory.service.IInventoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
//import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/inventory", produces = { MediaType.APPLICATION_JSON_VALUE })
//@AllArgsConstructor
@Validated
public class InventoryController {

	private IInventoryService iInventoryService;
	private InventoryContactInfoDto inventoryContactInfoDto;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private Environment environment; // Lab 11 configuration properties using Environment

	public InventoryController(IInventoryService iInventoryService, InventoryContactInfoDto inventoryContactInfoDto) {
		this.iInventoryService = iInventoryService;
		this.inventoryContactInfoDto = inventoryContactInfoDto;
	}

	@GetMapping("/contact-info")
	public ResponseEntity<InventoryContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(inventoryContactInfoDto);
	}
	
	@GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() { // Lab 11
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@GetMapping("/sayHello")
	public String sayHello() {
		return "Hello World, Inventory Service is up and running!";
	}

	// Lab 3
	@PostMapping()
	public ResponseEntity<ResponseDto> createGuitar(@Valid @RequestBody InventoryDto inventoryDto) {
		iInventoryService.createGuitar(inventoryDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(InventoryConstants.STATUS_201, InventoryConstants.MESSAGE_201));
	}

	@GetMapping()
	public ResponseEntity<InventoryDto> fetchGuitarDetails(@RequestParam String serialNumber) {
		InventoryDto inventoryDto = iInventoryService.fetchGuitar(serialNumber);
		return ResponseEntity.status(HttpStatus.OK).body(inventoryDto);
	}
}