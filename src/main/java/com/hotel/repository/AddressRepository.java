package com.hotel.repository;

import com.hotel.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByPostalCode(Long postalCode);
}
