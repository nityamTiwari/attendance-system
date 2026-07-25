package com.nityam.attendancesystem.attendance.controller;


import com.nityam.attendancesystem.attendance.dto.AttendanceResponse;
import com.nityam.attendancesystem.attendance.dto.ClockInResponse;
import com.nityam.attendancesystem.attendance.dto.ClockOutResponse;
import com.nityam.attendancesystem.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

     private  final AttendanceService attendanceService;


    @PostMapping("/clockIn")
    public ResponseEntity<?> clockIn(){
        ClockInResponse response = attendanceService.clockIn();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/clockout")
    public ResponseEntity<?> clockOut(){
        ClockOutResponse response = attendanceService.clockOut();
        return  ResponseEntity.ok(response);
    }

    @PostMapping("/history")
    public ResponseEntity<?> attendanceHistory(){
        List<AttendanceResponse> response =  attendanceService.attendanceHistory();
        return  ResponseEntity.ok(response);
    }

}
