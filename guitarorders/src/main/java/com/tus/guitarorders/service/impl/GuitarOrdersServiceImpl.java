// Lab 3
package com.tus.guitarorders.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDetailsDto;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.dto.InventoryDto;
import com.tus.guitarorders.dto.OrderDetailsDto;
import com.tus.guitarorders.dto.OrdersDto;
import com.tus.guitarorders.entity.Customer;
import com.tus.guitarorders.entity.Orders;
import com.tus.guitarorders.exception.CustomerAlreadyExistsException;
import com.tus.guitarorders.exception.ResourceNotFoundException;
import com.tus.guitarorders.mapper.CustomerMapper;
import com.tus.guitarorders.mapper.OrdersMapper;
import com.tus.guitarorders.repository.CustomerRepository;
import com.tus.guitarorders.repository.OrdersRepository;
import com.tus.guitarorders.service.IGuitarOrdersService;
import com.tus.guitarorders.service.client.InventoryFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuitarOrdersServiceImpl implements IGuitarOrdersService {

	/**
	 * InventoryFeignClient is injected to enable communication with the Inventory
	 * service using Feign. This client allows the Guitar Orders service to fetch
	 * inventory details related to orders, such as availability and stock levels.
	 * It is used in methods like fetchCustomerDetails and fetchOrderDetails to
	 * enrich the response with inventory information, providing a more
	 * comprehensive view of the customer's order and its associated inventory
	 * status. Lab 24 - Implemented InventoryFeignClient to call Inventory service
	 * for fetching inventory details in fetchCustomerDetails and fetchOrderDetails
	 * methods.
	 */
	private InventoryFeignClient inventoryFeignClient;

	/**
	 * OrdersRepository is injected to perform CRUD operations on Orders entities.
	 * It is used to save new orders, fetch existing orders based on serial number,
	 * update order details, and delete orders associated with a customer. This
	 * repository is essential for managing the lifecycle of orders in the
	 * application and maintaining the relationship between customers and their
	 * orders. Lab 3 - Used OrdersRepository to save new orders when creating orders
	 * and fetch orders when retrieving order details.
	 */
	private OrdersRepository ordersRepository;

	/**
	 * CustomerRepository is injected to perform CRUD operations on Customer
	 * entities. It is used to check for existing customers based on mobile number,
	 * save new customers, and fetch customer details when needed. This repository
	 * is essential for managing the relationship between customers and their orders
	 * in the application. Lab 3 - Used CustomerRepository to check for existing
	 * customers and save new customers when creating orders .
	 */
	private CustomerRepository customerRepository;

	/**
	 * Create a new order for a customer based on the provided CustomerDto. This
	 * method checks if a customer with the same mobile number already exists and
	 * throws an exception if so. If the customer is new, it saves the customer and
	 * creates a new order associated with that customer.
	 * 
	 * @param customerDto The details of the customer for whom the order is to be
	 *                    created
	 */
	// @Override
	public void createOrder(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalcustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if (optionalcustomer.isPresent()) {
			throw new CustomerAlreadyExistsException(
					GuitarOrdersConstants.MESSAGE_400_CUSTOMER_ALREADY_EXISTS + customerDto.getMobileNumber());
		}
		Customer savedCustomer = customerRepository.save(customer);
		ordersRepository.save(createNewOrder(savedCustomer));
	}

	/**
	 * Helper method to create a new order for a given customer. This method
	 * generates a random order number and sets the initial status and quantity for
	 * the order.
	 * 
	 * @param customer - Customer Object
	 * @return the new account details
	 */
	private Orders createNewOrder(Customer customer) {
		Orders newOrder = new Orders();
//        newOrder.setCustomerId(customer.getCustomerId());
		newOrder.setCustomer(customer);
		long randomOrderNumber = 1000000000L + new Random().nextInt(900000000);
		newOrder.setOrderNumber(randomOrderNumber);
		newOrder.setStatus(GuitarOrdersConstants.PENDING);
		newOrder.setQuantity(1);
		return newOrder;
	}

	/**
	 * Fetch details of a customer along with their order and inventory details
	 * using Feign client to call the Inventory service. Lab 24 - Implemented
	 * fetchOrderWithInventory method in IGuitarOrdersService and used it in
	 * GuitarOrdersController to handle requests for fetching order with inventory.
	 * 
	 * @param serialNumber The serial number of the order to be fetched
	 * @return CustomerDto containing customer, order, and inventory details
	 */
	@Override
	public CustomerDto fetchOrder(String serialNumber) {
		Orders orders = ordersRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "serialNumber", serialNumber));

		Customer customer = orders.getCustomer();

		if (customer == null) {
			throw new ResourceNotFoundException("Customer", "Order Serial Number", serialNumber);
		}
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setOrdersDto(OrdersMapper.mapToOrdersDto(orders, new OrdersDto()));
		return customerDto;
	}

	/**
	 * Fetch all orders along with their associated customer details. Lab 6 -
	 * Implemented fetchAllOrders method in IGuitarOrdersService and used it in
	 * GuitarOrdersController to handle requests for fetching all orders.
	 * 
	 * @return List of CustomerDto containing customer and order details
	 */
	@Override
	public List<CustomerDto> fetchAllOrders() {
		// 1. Fetch all orders from the repository
		List<Orders> ordersList = ordersRepository.findAll();
		List<CustomerDto> customerDtoList = new ArrayList<>();

		// 2. Iterate through orders to build the DTOs
		for (Orders orders : ordersList) {
			// Access the linked Customer object via the JPA relationship
			Customer customer = orders.getCustomer();

			if (customer != null) {
				CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
				customerDto.setOrdersDto(OrdersMapper.mapToOrdersDto(orders, new OrdersDto()));
				customerDtoList.add(customerDto);
			}
		}
		return customerDtoList;
	}

	/**
	 * Update an existing customer's details and their associated order based on the
	 * provided CustomerDto. Lab 7 - Implemented updateOrder method in
	 * IGuitarOrdersService and used it in GuitarOrdersController to handle update
	 * requests.
	 * 
	 * @param customerDto The details of the customer and order to be updated
	 * @return true if the update was successful, false otherwise
	 */
	@Override
	public boolean updateOrder(CustomerDto customerDto) {
		boolean isUpdated = false;
		OrdersDto ordersDto = customerDto.getOrdersDto();
		if (ordersDto != null) {
			Orders orders = ordersRepository.findById(ordersDto.getOrderNumber()).orElseThrow(
					() -> new ResourceNotFoundException("Order", "OrderNumber", ordersDto.getOrderNumber().toString()));
			OrdersMapper.mapToOrders(ordersDto, orders);
			orders = ordersRepository.save(orders);

//            Long customerId = orders.getCustomerId();            
//            Customer customer = customerRepository.findById(customerId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));
			Customer customer = orders.getCustomer();
			if (customer == null) {
				throw new ResourceNotFoundException("Customer", "OrderNumber", ordersDto.getOrderNumber().toString());
			}
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	/**
	 * Delete a customer and their associated orders based on the provided mobile
	 * number. Lab 7 - Implemented deleteOrder method in IGuitarOrdersService and
	 * used it in GuitarOrdersController to handle delete requests.
	 * 
	 * @param mobileNumber The mobile number of the customer to be deleted
	 * @return true if the deletion was successful, false otherwise
	 */
	@Override
	public boolean deleteOrder(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
//		ordersRepository.deleteByCustomerId(customer.getCustomerId());
		ordersRepository.deleteByCustomerCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}

	/**
	 * Fetch details of a customer along with their order and inventory details
	 * using Feign client to call the Inventory service. Lab 24 - Implemented
	 * fetchCustomerDetails method to fetch customer details along with
	 * 
	 * @param mobileNumber The mobile number of the customer to fetch details for
	 * @return CustomerDetailsDto containing customer, order, and inventory details
	 */
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
				new CustomerDetailsDto());

//        String serialNumber = customer.getOrders().get(0).getSerialNumber(); 
//        ResponseEntity<InventoryDto> inventoryDto = inventoryFeignClient.fetchInventoryDetails(serialNumber);
		ResponseEntity<InventoryDto> inventoryDto = inventoryFeignClient.fetchInventoryDetails("FEN12345678");
		if (inventoryDto != null && inventoryDto.getStatusCode().is2xxSuccessful()) {
			customerDetailsDto.setInventoryDto(inventoryDto.getBody());
		}

		return customerDetailsDto;
	}

	/**
	 * Lab 24 - Implemented fetchOrderDetails method to fetch order details along
	 * with inventory details using Feign client
	 * 
	 * @param serialNumber The serial number of the order to fetch details for
	 * @return OrderDetailsDto containing order and inventory details
	 */
	public OrderDetailsDto fetchOrderDetails(String serialNumber) {
		Orders orders = ordersRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "serialNumber", serialNumber));
		OrderDetailsDto orderDetailsDto = OrdersMapper.mapToOrderDetailsDto(orders, new OrderDetailsDto());

		ResponseEntity<InventoryDto> inventoryDto = inventoryFeignClient.fetchInventoryDetails(serialNumber);
		if (inventoryDto != null && inventoryDto.getStatusCode().is2xxSuccessful()) {
			orderDetailsDto.setInventoryDto(inventoryDto.getBody());
		}

		return orderDetailsDto;
	}
}
