package com.tus.guitarorders.dto;

import lombok.Data;

@Data
public class OrdersDto {
	private Long orderNumber;

    private Integer quantity;
		
	private String status;
}