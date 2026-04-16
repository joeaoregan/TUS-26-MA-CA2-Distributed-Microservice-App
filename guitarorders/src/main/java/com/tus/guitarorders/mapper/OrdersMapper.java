// Lab 3
package com.tus.guitarorders.mapper;

import com.tus.guitarorders.dto.OrdersDto;
import com.tus.guitarorders.entity.Orders;

public class OrdersMapper {
	public static OrdersDto mapToOrdersDto(Orders orders, OrdersDto ordersDto) {
		ordersDto.setOrderNumber(orders.getOrderNumber());
		ordersDto.setStatus(orders.getStatus());
		ordersDto.setQuantity(orders.getQuantity());
		return ordersDto;
	}

	public static Orders mapToOrders(OrdersDto ordersDto, Orders orders) {
		orders.setOrderNumber(ordersDto.getOrderNumber());
		orders.setStatus(ordersDto.getStatus());
		orders.setQuantity(ordersDto.getQuantity());
		return orders;
	}
}
