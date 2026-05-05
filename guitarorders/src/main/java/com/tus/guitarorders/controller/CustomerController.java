package com.tus.guitarorders.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tus.guitarorders.dto.CustomerDetailsDto;
import com.tus.guitarorders.service.ICustomersService;

import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final ICustomersService iCustomerService;

    public CustomerController(ICustomersService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    /**
     * Endpoint to fetch customer details based on mobile number. This method
     * validates the mobile number format and uses the correlation ID for
     * tracing. Lab 7 - Added	validation for mobile number format and used
     * correlation ID for logging. Lab 11 - Used Environment to access
     * configuration properties if needed.
     *
     * @param correlationId The correlation ID for tracing requests
     * @param mobileNumber The mobile number of the customer
     * @return ResponseEntity containing CustomerDetailsDto
     */
    @GetMapping("/customers")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("guitarstore-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber) {
        logger.debug("GuitarStore-correlation-id found: {}", correlationId);
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.ok(customerDetailsDto);
    }
}
