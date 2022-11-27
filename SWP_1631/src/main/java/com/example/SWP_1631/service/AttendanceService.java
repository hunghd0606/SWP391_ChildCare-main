package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Attendance;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    public List<Attendance> getAllAttendanceByIdKinder(Integer id);

    public void deleteAttendanceByIdKinder(Integer id);

    public List<Attendance> getAllAttendanceOfInputDay(Date checkindate);

    Optional<Attendance> getAttendanceByStudentIdAndDateAndTeacherId(int kinderId, Date checkindate, int accountId);

    public void save(Attendance attendance);

    List<Attendance> getAllAttendanceByIdKinderAndDateFromAndDateTo(Integer id, String dateFrom, String dateTo);
}
