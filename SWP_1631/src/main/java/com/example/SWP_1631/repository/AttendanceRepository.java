package com.example.SWP_1631.repository;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query("SELECT u FROM Attendance u WHERE u.studentId.KinderId= :id ")
    public List<Attendance> getAllAttendanceByIdKinder(@Param("id") Integer id);

    @Modifying
    @Query("DELETE  FROM Attendance s WHERE s.studentId.KinderId =:id")
    void deleteAttendanceByIdKinder(@Param("id") Integer id);

    @Query("SELECT u FROM Attendance u WHERE u.checkDate =:dateS ")
    List<Attendance> getAllAttendanceOfInputDay(@Param("dateS") Date dateS);

    @Query("SELECT u FROM Attendance u WHERE u.checkDate =:dateS AND u.studentId.KinderId =:kinderId AND u.teacherId.accountId =:accountId")
    Optional<Attendance> getAttendanceByStudentIdAndDateAndTeacherId(@Param("kinderId") int kinderId, @Param("dateS") Date dateS, @Param("accountId") int accountId);

    @Query("SELECT u FROM Attendance u WHERE u.studentId.KinderId= :id AND u.checkDate >=:dateFrom AND u.checkDate <=:dateTo AND u.status =:st")
    List<Attendance> getAllAttendanceByIdKinderAndDateFromAndDateTo(@Param("id") Integer id, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, @Param("st") Integer st);
}
