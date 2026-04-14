package com.tus.guitarorders.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.entity.Customer;
import com.tus.guitarorders.entity.Orders;
import com.tus.guitarorders.mapper.CustomerMapper;
import com.tus.guitarorders.repository.OrdersRepository;
import com.tus.guitarorders.repository.CustomerRepository;
import com.tus.guitarorders.service.IGuitarOrdersService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuitarOrdersServiceImpl implements IGuitarOrdersService {

    private OrdersRepository ordersRepository;
    private CustomerRepository customerRepository;

    //@Override
    public void createOrder(CustomerDto customerDto) {
    		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		customer.setCreatedAt(LocalDateTime.now());
		customer.setCreatedBy("default");
		customer.setUpdatedAt(LocalDateTime.now());
		customer.setUpdatedBy("default");
		Customer savedCustomer = customerRepository.save(customer);
		ordersRepository.save(createNewOrder(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Orders createNewOrder(Customer customer) {
        Orders newOrder = new Orders();
        newOrder.setCustomerId(customer.getCustomerId());
        long randomOrderNumber = 1000000000L + new Random().nextInt(900000000);
        newOrder.setOrderNumber(randomOrderNumber);
        newOrder.setStatus(GuitarOrdersConstants.PENDING);
        newOrder.setQuantity(1);
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setCreatedBy("default");
        newOrder.setUpdatedAt(LocalDateTime.now());
        newOrder.setUpdatedBy("default");
        return newOrder;
    }
}
