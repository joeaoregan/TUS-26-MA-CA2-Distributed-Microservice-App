package com.tus.guitarorders.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tus.guitarorders.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

	Optional<Orders> findByCustomerId(Long customerId);

	@Transactional
	@Modifying
	void deleteByCustomerId(Long customerId);
}
