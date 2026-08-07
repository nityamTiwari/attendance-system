package com.nityam.attendancesystem.employee.service.impl;

import com.nityam.attendancesystem.auth.dto.LoginRequest;
import com.nityam.attendancesystem.auth.dto.LoginResponse;
import com.nityam.attendancesystem.auth.dto.RegisterRequest;
import com.nityam.attendancesystem.auth.dto.RegisterResponse;
import com.nityam.attendancesystem.auth.jwt.JwtService;
import com.nityam.attendancesystem.auth.security.EmployeeUserDetails;
import com.nityam.attendancesystem.common.Role;
import com.nityam.attendancesystem.employee.entity.Employee;
import com.nityam.attendancesystem.employee.repository.EmployeeRepository;
import com.nityam.attendancesystem.employee.service.EmployeeService;
import com.nityam.attendancesystem.exception.EmailAlreadyExistsException;
import com.nityam.attendancesystem.exception.InvalidCredentialsException;
import com.nityam.attendancesystem.exception.PhoneAlreadyExistsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(employeeRepository.existsByEmail(request.getEmail())){
          throw new EmailAlreadyExistsException("Employee with this email already exists.");
        }
        if(employeeRepository.existsByPhone(request.getPhone())){
            throw  new PhoneAlreadyExistsException("Phone number is already registered.");
        }

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDob(request.getDob());
        employee.setGender(request.getGender());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setRole(Role.EMPLOYEE);

        Employee savedEmployee = employeeRepository.save(employee);

        String employeeId = "EMP%03d".formatted(savedEmployee.getId());
        savedEmployee.setEmployeeId(employeeId);
        savedEmployee = employeeRepository.save(savedEmployee);

        RegisterResponse response = new RegisterResponse();
        response.setEmployeeId(savedEmployee.getEmployeeId());
        response.setMessage("Employee registered Successfully");

        return response;
    }

    @Override
    public LoginResponse login(LoginRequest login) {
        if(!employeeRepository.existsByEmail(login.getEmail())){
            throw new EmailAlreadyExistsException("Email not Registered.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );

        EmployeeUserDetails userDetails = (EmployeeUserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(userDetails);

        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setMessage("Login Successfully");
        return response;
//       authenticationManager.authenticate(new
//               UsernamePasswordAuthenticationToken(
//                       login.getEmail(),
//                      login.getPassword() ));
//
//       Employee employee = employeeRepository
//               .findByEmail(login.getEmail()).orElseThrow(()->
//                       new EmailAlreadyExistsException("Invalid Credintials"));
//
//       String jwtToken = jwtService.generateToken((UserDetails) employee);
//
//
//        LoginResponse response = new LoginResponse();
//        response.setMessage("Login successful");
//        response.setToken(jwtToken);
//        return response;
    }
}
