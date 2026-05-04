package com.tus.guitarorders.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tus.guitarorders.dto.CustomerDetailsDto;
import com.tus.guitarorders.dto.InventoryDto;
import com.tus.guitarorders.dto.OrdersDto;
import com.tus.guitarorders.entity.Customer;
import com.tus.guitarorders.entity.Orders;
import com.tus.guitarorders.exception.ResourceNotFoundException;
import com.tus.guitarorders.mapper.CustomerMapper;
import com.tus.guitarorders.mapper.OrdersMapper;
import com.tus.guitarorders.repository.CustomerRepository;
import com.tus.guitarorders.repository.OrdersRepository;
import com.tus.guitarorders.service.ICustomersService;
import com.tus.guitarorders.service.client.InventoryFeignClient;

import lombok.AllArgsConstructor;

/**
 * Service implementation for handling customer-related operations. This service
 * interacts with the CustomerRepository to fetch customer details, the
 * OrdersRepository to fetch order details, and the InventoryFeignClient to
 * fetch inventory details based on the order's serial number. It also maps
 * entities to DTOs using CustomerMapper and OrdersMapper.
 */
@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

	private OrdersRepository ordersRepository;
	private CustomerRepository customerRepository;
	private InventoryFeignClient inventoryFeignClient;

	/**
	 * Fetch customer details based on the provided mobile number. This method
	 * retrieves the customer information, their associated orders, and the
	 * inventory details for the ordered item. It then maps these details into a
	 * CustomerDetailsDto to be returned to the caller.
	 *
	 * @param mobileNumber The mobile number of the customer whose details are to be
	 *                     fetched.
	 * @return A CustomerDetailsDto containing the customer's information, their
	 *         orders, and inventory details.
	 * @throws ResourceNotFoundException If no customer is found with the provided
	 *                                   mobile number or if no orders are found for
	 *                                   the customer.
	 */
	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Orders orders = ordersRepository.findByCustomerCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Order", "customerId", customer.getCustomerId().toString()));

		CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
				new CustomerDetailsDto());
		customerDetailsDto.setOrdersDto(OrdersMapper.mapToOrdersDto(orders, new OrdersDto()));

		ResponseEntity<InventoryDto> inventoryDtoResponseEntity = inventoryFeignClient
				.fetchInventoryDetails(orders.getSerialNumber());
		customerDetailsDto.setInventoryDto(inventoryDtoResponseEntity.getBody());

		return customerDetailsDto;
	}
}
