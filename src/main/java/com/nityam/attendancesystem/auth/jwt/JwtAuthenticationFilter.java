package com.nityam.attendancesystem.auth.jwt;

import com.nityam.attendancesystem.auth.security.EmployeeUserDetails;
import com.nityam.attendancesystem.auth.security.EmployeeUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final  EmployeeUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, EmployeeUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            System.out.println("auth == null");
            filterChain.doFilter(request,response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        String username = jwtService.extractUsername(jwtToken);


        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwtToken,userDetails)){

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);
    }


//    private  final JwtService jwtService;
//    public final EmployeeUserDetailsService employeeUserDetailsService;
//
//    public JwtAuthenticationFilter(JwtService jwtService, EmployeeUserDetailsService employeeUserDetailsService) {
//        this.jwtService = jwtService;
//        this.employeeUserDetailsService = employeeUserDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            filterChain.doFilter(request,response);
//            return;
//        }
//
//        String jwt = authHeader.substring(7);
//        String  username = jwtService.extractUsername(jwt);
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//
//        }
//
//
//    }
}
