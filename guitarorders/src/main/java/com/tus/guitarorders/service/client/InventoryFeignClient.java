package com.tus.guitarorders.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.tus.guitarorders.dto.InventoryDto;

@FeignClient(name = "inventory", fallback = InventoryFallback.class)
public interface InventoryFeignClient {

	/**
	 * Fetch inventory details for a given serial number. This method is mapped to a
	 * GET request to the /api/inventory endpoint of the inventory service. It takes
	 * a correlation ID from the request header for tracking purposes and a serial
	 * number as a request parameter to identify the specific guitar for which
	 * inventory details are being requested. The method returns a ResponseEntity
	 * containing an InventoryDto with the details of the requested guitar. If the
	 * inventory service is unavailable, the fallback method in InventoryFallback
	 * will be invoked to provide a default response.
	 * 
	 * @param correlationId the correlation ID for tracking the request
	 * @param serialNumber  the serial number of the guitar for which inventory
	 *                      details are requested
	 * @return a ResponseEntity containing the InventoryDto with the requested
	 *         details
	 */
	@GetMapping(value = "/api/inventory", consumes = "application/json")
	public ResponseEntity<InventoryDto> fetchInventoryDetails(
			@RequestHeader("guitarstore-correlation-id") String correlationId, @RequestParam String serialNumber);
}
