package com.tus.guitarorders.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderDetailsDto {

    @NotEmpty(message = "Serial number cannot be null or empty")
    @Pattern(regexp = "^[A-Z0-9]{8,12}$", message = "Serial number must be 8-12 alphanumeric characters")
    private String serialNumber;

    private Integer quantity;

    private String status;

    private InventoryDto inventoryDto;
    private CustomerDto customerDto;
}
