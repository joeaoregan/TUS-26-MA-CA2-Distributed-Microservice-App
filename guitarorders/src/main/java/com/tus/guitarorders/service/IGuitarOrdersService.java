package com.tus.guitarorders.service;

import com.tus.guitarorders.dto.CustomerDto;

public interface IGuitarOrdersService {
	void createOrder(CustomerDto customerDto);
	CustomerDto fetchOrder(String mobileNumber);
	boolean updateOrder(CustomerDto customerDto);
	boolean deleteOrder(String mobileNumber);
}
