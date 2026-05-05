package com.tus.guitarinventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.guitarinventory.entity.Guitar;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {

    /**
     * Custom finder method to locate a guitar by its serial number. This
     * follows the pattern in Lab 3 to find accounts by mobile number.
     */
    Optional<Guitar> findBySerialNumber(String serialNumber);

}
