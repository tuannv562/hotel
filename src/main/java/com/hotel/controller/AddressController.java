package com.hotel.controller;

import com.hotel.model.Address;
import com.hotel.model.Message;
import com.hotel.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "addresses")
public class AddressController {
    @Autowired
    private AddressRepository repository;

    @GetMapping
    @ResponseBody
    public List<Address> getAllAddresses() {
        return repository.findAll();
    }

    @GetMapping(value = "/{addressId}")
    @ResponseBody
    public ResponseEntity getAddress(@PathVariable Long addressId) {
        Address checkAddress = repository.findOne(addressId);
        if (checkAddress == null) {
            return new ResponseEntity(new Message("This address id does not exist"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(checkAddress, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{addressId}")
    @ResponseBody
    public ResponseEntity updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
        Address checkAddress = repository.findOne(addressId);
        if (checkAddress == null) {
            return new ResponseEntity(new Message("This address id does not exist"), HttpStatus.NOT_FOUND);
        } else {
            if (address.getAddress1() != null && !address.getAddress1().trim().isEmpty())
                checkAddress.setAddress1(address.getAddress1());
            if (address.getAddress2() != null && !address.getAddress2().trim().isEmpty())
                checkAddress.setAddress2(address.getAddress2());
            if (address.getAddress3() != null && !address.getAddress3().trim().isEmpty())
                checkAddress.setAddress3(address.getAddress3());
            if (address.getCity() != null && address.getCity().trim().isEmpty())
                checkAddress.setCity(address.getCity());
            if (address.getState() != null && !address.getState().trim().isEmpty())
                checkAddress.setState(address.getState());
            if (!address.getCountry().trim().isEmpty())
                checkAddress.setCountry(address.getCountry());
            if (address.getPostalCode() != null)
                checkAddress.setPostalCode(address.getPostalCode());
            return new ResponseEntity(repository.save(checkAddress), HttpStatus.OK);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createAddress(@RequestBody Address address) {
        Address checkAddress = repository.findByPostalCode(address.getPostalCode());
        if (checkAddress == null) {
            return new ResponseEntity(repository.save(address), HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new Message("This address already existed"), HttpStatus.CONFLICT);
        }
    }
}
