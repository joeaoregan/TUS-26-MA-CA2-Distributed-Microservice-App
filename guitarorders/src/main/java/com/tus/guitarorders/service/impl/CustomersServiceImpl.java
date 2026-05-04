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

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private OrdersRepository ordersRepository;
    private CustomerRepository customerRepository;
    private InventoryFeignClient inventoryFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Orders orders = ordersRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
                new CustomerDetailsDto());
        customerDetailsDto.setOrdersDto(OrdersMapper.mapToOrdersDto(orders, new OrdersDto()));

        ResponseEntity<InventoryDto> inventoryDtoResponseEntity = inventoryFeignClient.fetchInventoryDetails(orders.getSerialNumber());
        customerDetailsDto.setInventoryDto(inventoryDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
