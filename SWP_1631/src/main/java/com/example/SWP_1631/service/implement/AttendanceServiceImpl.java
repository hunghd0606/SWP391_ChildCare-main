package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Attendance;
import com.example.SWP_1631.repository.AttendanceRepository;
import com.example.SWP_1631.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAllAttendanceByIdKinder(Integer id) {
        List<Attendance> list = new ArrayList<>();
        try {
            list = attendanceRepository.getAllAttendanceByIdKinder(id);
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteAttendanceByIdKinder(Integer id) {
        try {
            attendanceRepository.deleteAttendanceByIdKinder(id);
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }

    }

    @Override
    public List<Attendance> getAllAttendanceOfInputDay(Date checkindate) {
        List<Attendance> list = new ArrayList<>();
        try {
            list = attendanceRepository.getAllAttendanceOfInputDay(checkindate);
        } catch (Exception e) {
            System.err.println("Error getAllAttendanceOfInputDay: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Optional<Attendance> getAttendanceByStudentIdAndDateAndTeacherId(int kinderId, Date checkindate, int accountId) {
        Optional<Attendance> acc = null;
        try {
            acc = attendanceRepository.getAttendanceByStudentIdAndDateAndTeacherId(kinderId, checkindate, accountId);
        } catch (Exception e) {
            System.out.println("Error getAttendanceByStudentIdAndDateAndTeacherId: " + e.getMessage());
        }
        return acc;
    }

    @Override
    public void save(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAllAttendanceByIdKinderAndDateFromAndDateTo(Integer id, String dateFrom, String dateTo) {
        List<Attendance> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFromX = sdf.parse(dateFrom);
            Date dateToX = sdf.parse(dateTo);
            list = attendanceRepository.getAllAttendanceByIdKinderAndDateFromAndDateTo(id, dateFromX, dateToX, 1);
        } catch (Exception e) {
        }
        return list;
    }
}
