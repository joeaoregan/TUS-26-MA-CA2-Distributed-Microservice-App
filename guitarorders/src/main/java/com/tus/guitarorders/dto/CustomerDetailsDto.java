package com.tus.guitarorders.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDetailsDto {

    @NotEmpty(message = "Name cannot be null or empty") // Lab 7
    @Size(min = 5, max = 30, message = "the length of the customer name should be between 5 and 30") // Lab 7
    private String name;

    @NotEmpty(message = "email address cannot be null or empty") // Lab 7
    @Email(message = "Email adderess should be a valid value") // Lab 7
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") // Lab 7
    private String mobileNumber;

    private OrdersDto ordersDto;
    private InventoryDto inventoryDto;
}
