package com.hotel.repository;

import com.hotel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdNumber(String idNumber);
}
