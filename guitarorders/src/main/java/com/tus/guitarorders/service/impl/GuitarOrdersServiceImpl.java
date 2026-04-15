package com.tus.guitarorders.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.dto.OrdersDto;
import com.tus.guitarorders.entity.Customer;
import com.tus.guitarorders.entity.Orders;
import com.tus.guitarorders.exception.CustomerAlreadyExistsException;
import com.tus.guitarorders.exception.ResourceNotFoundException;
import com.tus.guitarorders.mapper.CustomerMapper;
import com.tus.guitarorders.mapper.OrdersMapper;
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
    		Optional<Customer> optionalcustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
    		if (optionalcustomer.isPresent()) {
                throw new CustomerAlreadyExistsException(
                        "Customer already registered with given mobile Number " + customerDto.getMobileNumber());
            }
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
        return newOrder;
    }    
    
    @Override
    public CustomerDto fetchOrder(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Orders orders = ordersRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Order", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setOrdersDto(OrdersMapper.mapToOrdersDto(orders, new OrdersDto()));
        return customerDto;
    }
    
    @Override
    public boolean updateOrder(CustomerDto customerDto) {
        boolean isUpdated = false;
        OrdersDto ordersDto = customerDto.getOrdersDto();
        if (ordersDto != null) {
            Orders orders = ordersRepository.findById(ordersDto.getOrderNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Order", "OrderNumber",
                    ordersDto.getOrderNumber().toString()));
            OrdersMapper.mapToOrders(ordersDto, orders);
            orders = ordersRepository.save(orders);

            Long customerId = orders.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }


    @Override
    public boolean deleteOrder(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        ordersRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
