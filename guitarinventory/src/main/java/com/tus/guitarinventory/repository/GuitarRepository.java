package com.tus.guitarinventory.repository;

import com.tus.guitarinventory.entity.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {

    /**
     * Custom finder method to locate a guitar by its serial number.
     * This follows the pattern in Lab 3 where we find accounts by mobile number.
     */
    Optional<Guitar> findBySerialNumber(String serialNumber);

}