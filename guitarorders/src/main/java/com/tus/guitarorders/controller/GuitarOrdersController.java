package com.tus.guitarorders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.dto.ResponseDto;
import com.tus.guitarorders.service.IGuitarOrdersService;

import jakarta.validation.Valid; // Lab 7
import jakarta.validation.constraints.Pattern; // Lab 7
//import lombok.AllArgsConstructor; // Lab 10 commented out to implement constructor injection manually

@RestController
@RequestMapping(path = "/api/guitars", produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor // Lab 10 commented out to implement constructor injection manually
@Validated
public class GuitarOrdersController {

	private IGuitarOrdersService iGuitarOrdersService;

	@Value("${build.version}")
	private String buildVersion;
	
	@Autowired
	private Environment environment; // Lab 11 configuration properties using Environment
	
	// Lab 10 - Implement constructor injection for IGuitarOrdersService
	public GuitarOrdersController(IGuitarOrdersService iGuitarOrdersService) {
		this.iGuitarOrdersService = iGuitarOrdersService;
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
		return "Hello World";
	}

	@GetMapping()
	public ResponseEntity<CustomerDto> fetchOrderDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) { // Lab
																																	// 7
		CustomerDto customerDto = iGuitarOrdersService.fetchOrder(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	// Lab 3
	@PostMapping()
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) { // Lab 7
		iGuitarOrdersService.createOrder(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarOrdersConstants.STATUS_201, GuitarOrdersConstants.MESSAGE_201));
	}

	@PutMapping()
	public ResponseEntity<ResponseDto> updateOrderDetails(@Valid @RequestBody CustomerDto customerDto) { // Lab 7
		boolean isUpdated = iGuitarOrdersService.updateOrder(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_200, GuitarOrdersConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_500, GuitarOrdersConstants.MESSAGE_500));
		}
	}

	@DeleteMapping()
	public ResponseEntity<ResponseDto> deleteOrderDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) { // Lab
																																	// 7
		boolean isDeleted = iGuitarOrdersService.deleteOrder(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_200, GuitarOrdersConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_500, GuitarOrdersConstants.MESSAGE_500));
		}
	}
}
