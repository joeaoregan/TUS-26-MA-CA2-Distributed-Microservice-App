package com.tus.guitarorders.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.dto.OrdersContactInfoDto;
import com.tus.guitarorders.dto.ResponseDto;
import com.tus.guitarorders.service.IGuitarOrdersService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid; // Lab 7
import jakarta.validation.constraints.Pattern; // Lab 7
//import lombok.AllArgsConstructor; // Lab 10 commented out to implement constructor injection manually

@RestController
@RequestMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor // Lab 10 commented out to implement constructor injection manually
@Validated
public class GuitarOrdersController {

	private static final Logger logger = LoggerFactory.getLogger(GuitarOrdersController.class);
	/**
	 * Guitar Orders service to handle business logic related to guitar orders
	 * management. Lab 3 - Implemented IGuitarOrdersService and used it in this
	 * controller
	 */
	private IGuitarOrdersService iGuitarOrdersService;

	/**
	 * Orders contact information DTO Lab 11 - Inject OrdersContactInfoDto using
	 * constructor injection
	 */
	private OrdersContactInfoDto ordersContactInfoDto;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private Environment environment; // Lab 11 configuration properties using Environment

	// Lab 10 - Implement constructor injection for IGuitarOrdersService
	public GuitarOrdersController(IGuitarOrdersService iGuitarOrdersService,
			OrdersContactInfoDto ordersContactInfoDto) {
		this.iGuitarOrdersService = iGuitarOrdersService;
		this.ordersContactInfoDto = ordersContactInfoDto; // Lab 11
	}

	@GetMapping("/contact-info")
	public ResponseEntity<OrdersContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(ordersContactInfoDto);
	}

	@RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback") // Lab 34 - Apply rate limiting to
																						// this endpoint
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion() { // Lab 11
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
	}

	public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
		return ResponseEntity.status(HttpStatus.OK).body("JAVA 17");
	}

	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@GetMapping("/sayHello")
	public String sayHello() {
		return "Hello World, Guitar Orders Service is up and running!";
	}

	/**
	 * Fetch order details based on the provided serial number. Lab 7 - Added.
	 * 
	 * @param serialNumber The serial number of the guitar order to be fetched
	 * @return ResponseEntity containing the CustomerDto with order details and HTTP
	 *         status
	 */
	@GetMapping("/{serialNumber}")
	public ResponseEntity<CustomerDto> fetchOrderDetails(
			@RequestHeader("guitarstore-correlation-id") String correlationId,
			@PathVariable @Pattern(regexp = "^[A-Z0-9]{8,12}$", message = "Serial number must be 8-12 alphanumeric characters") String serialNumber) {
		logger.debug("GuitarStore-correlation-id found in fetchOrderDetails: {}", correlationId);
		CustomerDto customerDto = iGuitarOrdersService.fetchOrder(serialNumber, correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	/**
	 * Fetch details of all guitar orders.
	 * 
	 * @return ResponseEntity containing a list of CustomerDto objects for all
	 *         orders and HTTP status
	 */
	@GetMapping()
	public ResponseEntity<List<CustomerDto>> fetchAllOrders(
			@RequestHeader("guitarstore-correlation-id") String correlationId) {
		logger.debug("GuitarStore-correlation-id found in fetchAllOrders: {}", correlationId);
		List<CustomerDto> orders = iGuitarOrdersService.fetchAllOrders(correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}

	/**
	 * Create a new guitar order based on the provided customer details and order
	 * information. Lab 3 - Implemented createOrder method in IGuitarOrdersService
	 * and used it in this controller method
	 * 
	 * @param customerDto The details of the customer and order to be created
	 * @return ResponseEntity with HTTP status indicating the result of the create
	 *         operation
	 */
	@PostMapping()
	public ResponseEntity<ResponseDto> createOrder(@Valid @RequestBody CustomerDto customerDto) { // Lab 7
		iGuitarOrdersService.createOrder(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(GuitarOrdersConstants.STATUS_201, GuitarOrdersConstants.MESSAGE_201));
	}

	/**
	 * Update an existing guitar order based on the provided customer details and
	 * order information. Lab 7 - Implemented updateOrder method in
	 * IGuitarOrdersService and used it in this controller method
	 * 
	 * @param customerDto The details of the customer and order to be updated
	 * @return ResponseEntity with HTTP status indicating the result of the update
	 *         operation
	 */
	@PutMapping()
	public ResponseEntity<ResponseDto> updateOrderDetails(@Valid @RequestBody CustomerDto customerDto) {
		boolean isUpdated = iGuitarOrdersService.updateOrder(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_200, GuitarOrdersConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_500, GuitarOrdersConstants.MESSAGE_500));
		}
	}

	/**
	 * Delete an existing guitar order based on the provided serial number. Lab 7 -
	 * Implemented deleteOrder method in IGuitarOrdersService and used it in this
	 * controller method
	 * 
	 * @param serialNumber The serial number of the guitar order to be deleted
	 * @return ResponseEntity with HTTP status indicating the result of the delete
	 *         operation
	 */
	@DeleteMapping()
	public ResponseEntity<ResponseDto> deleteOrderDetails(
			@RequestParam @Pattern(regexp = "^[A-Z0-9]{8,12}$", message = "Serial number must be 8-12 alphanumeric characters") String serialNumber) { // Lab
																																						// 7
		boolean isDeleted = iGuitarOrdersService.deleteOrder(serialNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_200, GuitarOrdersConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(GuitarOrdersConstants.STATUS_500, GuitarOrdersConstants.MESSAGE_500));
		}
	}
}
