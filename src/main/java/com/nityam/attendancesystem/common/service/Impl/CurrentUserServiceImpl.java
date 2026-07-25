package com.nityam.attendancesystem.common.service.Impl;

import com.nityam.attendancesystem.auth.security.EmployeeUserDetails;
import com.nityam.attendancesystem.common.service.CurrentUserService;
import com.nityam.attendancesystem.employee.entity.Employee;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {


    @Override
    public Employee getCurrentEmployee() {

        Authentication authenticaton = SecurityContextHolder.getContext().getAuthentication();
        EmployeeUserDetails userDetails = (EmployeeUserDetails) authenticaton.getPrincipal();
        Employee employee = userDetails.getEmployee();
        return employee;
    }
}
