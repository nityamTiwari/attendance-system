package com.nityam.attendancesystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI attendanceSystemOpenAPI(){

        return  new OpenAPI()
                .info(new Info()
                        .title("Employee Attendance System API")
                        .description("REST API for Employee Attendance Management using Spring Boot, JWT Authentication, and MySQL.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Nityam Kumar Tiwari")
                                .email("nityamtiwari08@gmail.com")));
    }
}
