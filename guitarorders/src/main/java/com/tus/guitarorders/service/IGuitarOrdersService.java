// Lab 3
package com.tus.guitarorders.service;

import java.util.List;

import com.tus.guitarorders.dto.CustomerDto;

public interface IGuitarOrdersService {
	void createOrder(CustomerDto customerDto);
	CustomerDto fetchOrder(String serialNumber); // was fetchOrder(String mobileNumber)
	boolean updateOrder(CustomerDto customerDto);
	boolean deleteOrder(String mobileNumber);
	
	public List<CustomerDto> fetchAllOrders();
}
