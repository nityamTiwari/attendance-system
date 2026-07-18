package com.nityam.attendancesystem.employee.repository;

import com.nityam.attendancesystem.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}

