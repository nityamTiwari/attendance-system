package com.nityam.attendancesystem.attendance.service.impl;

import com.nityam.attendancesystem.attendance.dto.ClockInResponse;
import com.nityam.attendancesystem.attendance.entity.Attendance;
import com.nityam.attendancesystem.attendance.repository.AttendanceRepository;
import com.nityam.attendancesystem.attendance.service.AttendanceService;
import com.nityam.attendancesystem.auth.security.EmployeeUserDetails;
import com.nityam.attendancesystem.common.AttendanceStatus;
import com.nityam.attendancesystem.employee.entity.Employee;
import com.nityam.attendancesystem.exception.AlreadyClockedInException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;


    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }


    @Override
    public ClockInResponse clockIn() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeUserDetails userDetails = (EmployeeUserDetails) authentication.getPrincipal();
        Employee employee = userDetails.getEmployee();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        Optional<Attendance> existingAttendance =
                attendanceRepository.findByEmployeeAndAttendanceDate(employee,today);
        if(existingAttendance.isPresent()){
            throw  new AlreadyClockedInException("You have already ClockedIn today");
        }

        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(today);
        attendance.setClockIn(now);
        attendance.setEmployee(employee);
        attendance.setStatus(AttendanceStatus.ACTIVE);

        Attendance saveAttendance = attendanceRepository.save(attendance);

        ClockInResponse clockInResponse = new ClockInResponse();
        clockInResponse.setMessage("Clock In Successful");
        clockInResponse.setClockIn(saveAttendance.getClockIn());

        return clockInResponse;
    }
}
