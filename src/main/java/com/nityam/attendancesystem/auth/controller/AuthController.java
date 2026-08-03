package com.nityam.attendancesystem.auth.controller;

import com.nityam.attendancesystem.auth.dto.LoginRequest;
import com.nityam.attendancesystem.auth.dto.LoginResponse;
import com.nityam.attendancesystem.auth.dto.RegisterRequest;
import com.nityam.attendancesystem.auth.dto.RegisterResponse;
import com.nityam.attendancesystem.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final EmployeeService employeeService;

    public AuthController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = employeeService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/hello")
    public String hello() {
        return "CI/CD Working";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        System.out.println("hit login controller");

        LoginResponse request1 = employeeService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(request1);

//        LoginResponse response = employeeService.login(request);
//        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
