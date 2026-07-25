package com.nityam.attendancesystem.attendance.service;

import com.nityam.attendancesystem.attendance.dto.AttendanceResponse;
import com.nityam.attendancesystem.attendance.dto.ClockInResponse;
import com.nityam.attendancesystem.attendance.dto.ClockOutResponse;

import java.util.List;

public interface AttendanceService {

    ClockInResponse clockIn();
    ClockOutResponse clockOut();
    List<AttendanceResponse> attendanceHistory();
}
