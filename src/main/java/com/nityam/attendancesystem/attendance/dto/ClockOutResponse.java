package com.nityam.attendancesystem.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClockOutResponse {

    private String message;
    private LocalDateTime clockOut;
    private  Integer workingMinutes;

}
