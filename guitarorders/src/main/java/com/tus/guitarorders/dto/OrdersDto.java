package com.tus.guitarorders.dto;

import jakarta.validation.constraints.Max; // Lab 7
import jakarta.validation.constraints.Min; // Lab 7
import jakarta.validation.constraints.NotEmpty; // Lab 7
import jakarta.validation.constraints.NotNull; // Lab 7
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrdersDto {

    @NotNull(message = "OrderNumber cannot be null") // Lab 7
    @Min(value = 1000000000L, message = "OrderNumber must be 10 digits") // Lab 7
    @Max(value = 9999999999L, message = "OrderNumber must be 10 digits") // Lab 7
    private Long orderNumber;

    @Positive(message = "Quantity must be greater than zero") // Lab 7
    private Integer quantity;

    @NotEmpty(message = "Status cannot be null or empty") // Lab 7
    private String status;
}
