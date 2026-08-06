package com.nityam.attendancesystem.attendance.dto;


import com.nityam.attendancesystem.common.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodayAttendanceResponse {

    private LocalDate attendanceDate;

    private LocalDateTime clockIn;

    private LocalDateTime clockOut;

    private Integer workingMinutes;

    private AttendanceStatus status;
}
