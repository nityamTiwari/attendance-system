package com.nityam.attendancesystem.attendance.repository;

import com.nityam.attendancesystem.attendance.entity.Attendance;
import com.nityam.attendancesystem.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Optional<Attendance> findByEmployeeAndAttendanceDate(Employee employee, LocalDate date);

}
