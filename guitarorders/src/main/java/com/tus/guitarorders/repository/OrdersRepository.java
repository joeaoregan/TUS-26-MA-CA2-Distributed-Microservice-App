package com.tus.guitarorders.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.tus.guitarorders.entity.GuitarOrders;

public interface OrdersRepository extends JpaRepository<GuitarOrders, Long> {

	Optional<GuitarOrders> findByCustomerId(Long customerId);

	@Transactional
	@Modifying
	void deleteByCustomerId(Long customerId);
}
