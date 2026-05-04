package com.tus.guitarorders.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tus.guitarorders.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

	/**
	 * Orders is linked to Customer via a ManyToOne relationship, we can use the
	 * customer's ID to find the order. The method name follows Spring Data JPA's
	 * naming conventions, allowing it to automatically generate the query.
	 */
	Optional<Orders> findByCustomerCustomerId(Long customerId);

	/**
	 * Find an order by its serial number. The method name follows Spring Data JPA's
	 * naming conventions, allowing it to automatically generate the query.
	 */
	Optional<Orders> findBySerialNumber(String serialNumber);

	/**
	 * Delete an order by the customer's ID. The method name follows Spring Data
	 * JPA's naming conventions, allowing it to automatically generate the query.
	 * The
	 * 
	 * @Modifying annotation indicates that this method will perform a delete
	 *            operation, and @Transactional ensures that the operation is
	 *            executed within a transaction.
	 */
	@Transactional
	void deleteByCustomerCustomerId(Long customerId);
}
