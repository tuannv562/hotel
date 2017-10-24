package com.hotel.controller;

import com.hotel.model.*;
import com.hotel.repository.AddressRepository;
import com.hotel.repository.EmployeeRepository;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/{employeeId}")
    @ResponseBody
    public ResponseEntity getEmployee(@PathVariable Long employeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        if (employee == null)
            return new ResponseEntity(new Message("Employee ID does not exist"), HttpStatus.NOT_FOUND);
        else return new ResponseEntity(employee, HttpStatus.OK);
    }

    @PutMapping(value = "/{employeeId}")
    @ResponseBody
    public ResponseEntity updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDTO) {
        Address address = addressRepository.findOne(employeeDTO.getAddressId());
        if (address == null) return new ResponseEntity(new Message("Address ID does not exist"), HttpStatus.NOT_FOUND);
        Hotel hotel = hotelRepository.findOne(employeeDTO.getHotelId());
        if (hotel == null) return new ResponseEntity(new Message("Hotel ID does not exist"), HttpStatus.NOT_FOUND);
        Employee employee = employeeRepository.findByIdNumber(employeeDTO.getIdNumber());
        if (employee == null) {
            return new ResponseEntity(new Message("Employee ID does not exist"), HttpStatus.NOT_FOUND);
        } else {
            employee.setName(employeeDTO.getName());
            employee.setDateOfBirth(employeeDTO.getDateOfBirth());
            employee.setGender(employeeDTO.getGender());
            employee.setIdNumber(employeeDTO.getIdNumber());
            employee.setAddress(address);
            employee.setHotel(hotel);
            return new ResponseEntity(employeeRepository.save(employee), HttpStatus.OK);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Address address = addressRepository.findOne(employeeDTO.getAddressId());
        if (address == null) return new ResponseEntity(new Message("Address ID does not exist"), HttpStatus.NOT_FOUND);
        Hotel hotel = hotelRepository.findOne(employeeDTO.getHotelId());
        if (hotel == null) return new ResponseEntity(new Message("Hotel ID does not exist"), HttpStatus.NOT_FOUND);
        Employee employee = employeeRepository.findByIdNumber(employeeDTO.getIdNumber());
        if (employee == null) {
            Employee addEmployee = new Employee(employeeDTO.getName(), employeeDTO.getDateOfBirth(),
                    employeeDTO.getGender(), employeeDTO.getIdNumber());
            addEmployee.setAddress(address);
            addEmployee.setHotel(hotel);
            return new ResponseEntity(employeeRepository.save(addEmployee), HttpStatus.CREATED);
        } else return new ResponseEntity(new Message("ID number already existed"), HttpStatus.CONFLICT);
    }
}
