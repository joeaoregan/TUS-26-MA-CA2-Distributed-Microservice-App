package com.tus.guitarorders.service;

import com.tus.guitarorders.dto.CustomerDto;

public interface IGuitarOrdersService {
	void createOrder(CustomerDto customerDto);
}
