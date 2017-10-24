package com.hotel.controller;

import com.hotel.model.Address;
import com.hotel.model.Hotel;
import com.hotel.model.HotelDTO;
import com.hotel.model.Message;
import com.hotel.repository.AddressRepository;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "hotels")
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    @ResponseBody
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping(value = "/{hotelId}")
    @ResponseBody
    public ResponseEntity getHotel(@PathVariable Long hotelId) {
        Hotel checkHotel = hotelRepository.findOne(hotelId);
        if (checkHotel == null) return new ResponseEntity(new Message("Hotel ID does not exist"), HttpStatus.NOT_FOUND);
        else return new ResponseEntity(checkHotel, HttpStatus.OK);
    }

    @PutMapping(value = "/{hotelId}")
    @ResponseBody
    public ResponseEntity updateHotel(@PathVariable Long hotelId, @RequestBody HotelDTO hotelDTO) {
        Address address = addressRepository.findOne(hotelDTO.getAddressId());
        if (address == null) return new ResponseEntity(new Message("Address ID does not exist"), HttpStatus.NOT_FOUND);
        Hotel hotel = hotelRepository.findOne(hotelId);
        if (hotel == null) return new ResponseEntity(new Message("Hotel ID does not exist"), HttpStatus.NOT_FOUND);
        else {
            hotel.setName(hotelDTO.getName());
            hotel.setAddress(address);
            return new ResponseEntity(hotelRepository.save(hotel), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity createHotel(@RequestBody HotelDTO hotelDTO) {
        Address address = addressRepository.findOne(hotelDTO.getAddressId());
        if (address == null) return new ResponseEntity(new Message("Address ID does not exist"), HttpStatus.CONFLICT);
        Hotel checkHotel = hotelRepository.findByAddressId(hotelDTO.getAddressId());
        if (checkHotel == null) {
            Hotel hotel = new Hotel(hotelDTO.getName(), address);
            hotel.setAddress(address);
            return new ResponseEntity(hotelRepository.save(hotel), HttpStatus.CREATED);
        } else return new ResponseEntity(new Message("There is a hotel in this place"), HttpStatus.CONFLICT);
    }
}
