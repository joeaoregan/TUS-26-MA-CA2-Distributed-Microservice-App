package com.tus.guitarinventory.controller;

import com.tus.guitarinventory.constants.InventoryConstants;
import com.tus.guitarinventory.dto.InventoryContactInfoDto;
import com.tus.guitarinventory.dto.InventoryDto;
import com.tus.guitarinventory.dto.ResponseDto;
import com.tus.guitarinventory.service.IInventoryService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

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
		logger.debug("Invoked inventory contact-info API");
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

	/**
	 * Lab 3: Create a new guitar entry in the inventory using the provided
	 * InventoryDto. The method validates the incoming request body and returns a
	 * ResponseEntity with a status of CREATED and a response message.
	 *
	 * @param inventoryDto The InventoryDto containing the details of the guitar to
	 *                     be created, validated for correctness.
	 * @return ResponseEntity containing a ResponseDto with status and message
	 *         indicating the result of the creation operation.
	 */
	@PostMapping()
	public ResponseEntity<ResponseDto> createGuitar(@Valid @RequestBody InventoryDto inventoryDto) {
		iInventoryService.createGuitar(inventoryDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(InventoryConstants.STATUS_201, InventoryConstants.MESSAGE_201));
	}

	/**
	 * Fetch guitar details based on the serial number provided as a query
	 * parameter. The correlation ID is extracted from the request header for
	 * logging purposes. The method returns the guitar details wrapped in a
	 * ResponseEntity with an HTTP status of OK. Lab 11: Added a new endpoint to
	 * fetch the Java version from the environment properties. Lab 24: Added logging
	 * for the correlation ID to trace the request flow through the system.
	 *
	 * @param correlationId The correlation ID from the request header for tracing
	 *                      and logging.
	 * @param serialNumber  The serial number of the guitar to fetch details for,
	 *                      provided as a query parameter.
	 * @return ResponseEntity containing the InventoryDto with guitar details and an
	 *         HTTP status of OK.
	 */
	@GetMapping()
	public ResponseEntity<InventoryDto> fetchGuitarDetails(
			@RequestHeader("guitarstore-correlation-id") String correlationId, @RequestParam String serialNumber) {

		logger.debug("guitarstore-correlation-id found in InventoryController: {}", correlationId);

		InventoryDto inventoryDto = iInventoryService.fetchGuitar(serialNumber);
		System.out.println("Build Version: " + buildVersion);
		return ResponseEntity.status(HttpStatus.OK).body(inventoryDto);
	}
}