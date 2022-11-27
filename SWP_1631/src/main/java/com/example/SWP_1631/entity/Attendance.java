package com.example.SWP_1631.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Kindergartner studentId;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "")
    @Column(name = "check_date")
    private Date checkDate;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Account teacherId;

    public Date getDate() {
        return checkDate;
    }

    public void checkDate(String checkDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.checkDate = sdf.parse(checkDate);
        } catch (Exception e) {
            String date = "";
            Date date1 = sdf.parse(checkDate);
            this.checkDate = date1;
        }
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", studentId=" + studentId +
                ", checkDate=" + checkDate +
                ", status=" + status +
                ", teacherId=" + teacherId +
                '}';
    }
}
