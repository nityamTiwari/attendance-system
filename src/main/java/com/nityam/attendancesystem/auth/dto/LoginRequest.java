package com.nityam.attendancesystem.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {


    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String Email;
    @NotBlank(message =  "Please enter the password to login")
    private String  password;
}
