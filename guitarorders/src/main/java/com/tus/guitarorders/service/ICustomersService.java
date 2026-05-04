package com.tus.guitarorders.service;

import com.tus.guitarorders.dto.CustomerDetailsDto;

public interface ICustomersService {

	CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
