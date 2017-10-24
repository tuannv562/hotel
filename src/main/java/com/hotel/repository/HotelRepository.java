package com.hotel.repository;

import com.hotel.model.Address;
import com.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Hotel findByAddressId(Long addressId);
}
