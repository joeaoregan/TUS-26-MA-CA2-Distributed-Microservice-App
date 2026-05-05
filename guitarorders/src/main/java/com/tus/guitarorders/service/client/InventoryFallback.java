package com.tus.guitarorders.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.tus.guitarorders.dto.InventoryDto;

@Component
public class InventoryFallback implements InventoryFeignClient {

	/**
	 * Fallback method for fetching inventory details when the inventory service is
	 * unavailable. This method returns a default InventoryDto with a message
	 * indicating that the information is temporarily unavailable, along with the
	 * provided serial number and a price of 0.
	 *
	 * @param correlationId the correlation ID for tracking the request
	 * @param serialNumber  the serial number of the guitar for which inventory
	 *                      details were requested
	 * @return a ResponseEntity containing the fallback InventoryDto
	 */
	@Override
	public ResponseEntity<InventoryDto> fetchInventoryDetails(String correlationId, String serialNumber) {
//		InventoryDto fallback = new InventoryDto();
//		fallback.setBrand("Guitar Information Temporarily Unavailable");
//		fallback.setPrice(0);
//		fallback.setSerialNumber(serialNumber);
//		return ResponseEntity.ok(fallback);
		return null; // Lab 31
	}
}