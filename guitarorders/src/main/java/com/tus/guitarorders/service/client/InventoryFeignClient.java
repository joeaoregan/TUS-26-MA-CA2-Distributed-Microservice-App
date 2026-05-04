package com.tus.guitarorders.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.tus.guitarorders.dto.InventoryDto;

@FeignClient("inventory")
public interface InventoryFeignClient {
	@GetMapping(value = "/api/inventory", consumes = "application/json")
	public ResponseEntity<InventoryDto> fetchInventoryDetails(@RequestHeader("guitarstore-correlation-id") String correlationId, @RequestParam String serialNumber);
}
