package com.nityam.attendancesystem.attendance.service.impl;

import com.nityam.attendancesystem.attendance.dto.AttendanceResponse;
import com.nityam.attendancesystem.attendance.dto.ClockInResponse;
import com.nityam.attendancesystem.attendance.dto.ClockOutResponse;
import com.nityam.attendancesystem.attendance.entity.Attendance;
import com.nityam.attendancesystem.attendance.repository.AttendanceRepository;
import com.nityam.attendancesystem.attendance.service.AttendanceService;
import com.nityam.attendancesystem.common.AttendanceStatus;
import com.nityam.attendancesystem.common.service.CurrentUserService;
import com.nityam.attendancesystem.employee.entity.Employee;
import com.nityam.attendancesystem.exception.AlreadyClockedInException;
import com.nityam.attendancesystem.exception.AlreadyClockedOutException;
import com.nityam.attendancesystem.exception.AttendanceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final CurrentUserService currentUserService;

    private static final Logger log =
            LoggerFactory.getLogger(AttendanceServiceImpl.class);


    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, CurrentUserService currentUserService) {
        this.attendanceRepository = attendanceRepository;
        this.currentUserService = currentUserService;
    }


    @Transactional
    @Override
    public ClockInResponse clockIn() {

        Employee employee = currentUserService.getCurrentEmployee();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        Optional<Attendance> existingAttendance =
                attendanceRepository.findByEmployeeAndAttendanceDate(employee,today);
        if(existingAttendance.isPresent()){
            log.warn("Employee {} attempted to clock in twice on {}.",employee.getEmployeeId(),today);
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

        log.info("Employee {} clocked in successfully.",employee.getEmployeeId());
        return clockInResponse;
    }

    @Transactional
    @Override
    public ClockOutResponse clockOut() {

        Employee employee = currentUserService.getCurrentEmployee();
        LocalDate today = LocalDate.now();
        Optional<Attendance> existingAttendance  =
                attendanceRepository.findByEmployeeAndAttendanceDate(employee,today);

        if (existingAttendance.isEmpty()){

            log.warn("Employee {} has no Attendance logs for today {}", employee.getEmployeeId(),today);
            throw new AttendanceNotFoundException("No attendance recorded for today.");
        }

        AttendanceStatus status = existingAttendance.get().getStatus();
         if(status == AttendanceStatus.COMPLETED){

             log.warn("Employee {} attempted to clock in twice on {}.",
                     employee.getEmployeeId(),
                     today);
             throw new AlreadyClockedOutException("You are already clocked-out.");
         }
         Attendance attendance = existingAttendance.get();

         LocalDateTime clockIn = attendance.getClockIn();
         LocalDateTime clockOut = LocalDateTime.now();
         Duration duration = Duration.between(clockIn,clockOut);
         int workingMinutes = (int) duration.toMinutes();


         attendance.setClockOut(clockOut);
         attendance.setWorkingMinutes(workingMinutes);
         attendance.setStatus(AttendanceStatus.COMPLETED);

        Attendance updateAttendance = attendanceRepository.save(attendance);

         ClockOutResponse response = new ClockOutResponse();
         response.setClockOut(updateAttendance.getClockOut());
         response.setMessage("you are clocked-out.");
         response.setWorkingMinutes(updateAttendance.getWorkingMinutes());

         log.info("\"Employee {} clocked out successfully. Working minutes: {}",
                 employee.getEmployeeId(),workingMinutes);
        return response;
    }

    @Override
    public List<AttendanceResponse> attendanceHistory() {
        Employee employee = currentUserService.getCurrentEmployee();

        List<Attendance> attendanceHistory = attendanceRepository.findByEmployeeOrderByAttendanceDateDesc(employee);
        List<AttendanceResponse> responses = new ArrayList<>();

       for (Attendance attendance : attendanceHistory){
           AttendanceResponse response = new AttendanceResponse();
           response.setAttendanceDate(attendance.getAttendanceDate());
           response.setStatus(attendance.getStatus());
           response.setClockIn(attendance.getClockIn());
           response.setClockOut(attendance.getClockOut());
           response.setWorkingMinutes(attendance.getWorkingMinutes());
           responses.add(response);
       }
        log.info("Attendance history fetched for employee {}.",
                employee.getEmployeeId());
        return responses;
    }
}
