package com.nityam.attendancesystem.employee.service;

import com.nityam.attendancesystem.auth.dto.LoginRequest;
import com.nityam.attendancesystem.auth.dto.LoginResponse;
import com.nityam.attendancesystem.auth.dto.RegisterRequest;
import com.nityam.attendancesystem.auth.dto.RegisterResponse;

public interface EmployeeService {

        RegisterResponse register(RegisterRequest request);
        LoginResponse login(LoginRequest login);
}
