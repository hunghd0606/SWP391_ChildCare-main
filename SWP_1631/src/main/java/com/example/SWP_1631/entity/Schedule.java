package com.example.SWP_1631.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Clazz clazzId;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activityid;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slotId;


    @Column(name = "schedule_date")
    private String scheduleDate;


}
