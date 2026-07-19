package com.nityam.attendancesystem.attendance.controller;


import com.nityam.attendancesystem.attendance.dto.ClockInResponse;
import com.nityam.attendancesystem.attendance.service.AttendanceService;
import com.nityam.attendancesystem.attendance.service.impl.AttendanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
