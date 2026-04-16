package com.tus.guitarinventory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class InventoryDto {

    @NotEmpty(message = "Model name cannot be null or empty")
    private String modelName;

    @NotEmpty(message = "Brand cannot be null or empty")
    private String brand;

    @NotEmpty(message = "Serial number cannot be null or empty")
    @Pattern(regexp = "^[A-Z0-9]{8,12}$", message = "Serial number must be 8-12 alphanumeric characters")
    private String serialNumber;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than zero")
    private Integer price;

    @NotNull(message = "Total stock cannot be null")
    @PositiveOrZero(message = "Total stock must be zero or greater")
    private Integer totalStock;

    @NotNull(message = "Available stock cannot be null")
    @PositiveOrZero(message = "Available stock must be zero or greater")
    private Integer availableStock;
}