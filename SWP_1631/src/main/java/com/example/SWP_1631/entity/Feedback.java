package com.example.SWP_1631.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;

    @ManyToOne
    @JoinColumn(name = "kid_id")
    private Kindergartner kidId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Account teacherId;

    @Column(name = "fb_content")
    private String fbContent;

    @Column(name = "rating")
    private double rating;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "")
    @Column(name = "fb_date")
    private Date fbDate;

    public Date getDob() {
        return fbDate;
    }

    public void setDob(String dob) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.fbDate = formatter.parse(dob);
        } catch (Exception e) {
            String sDate = "17/07/2017";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
            this.fbDate = date;
        }

    }

}
