package com.example.SWP_1631.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "teacher_achiverment")
public class TeacherAchiverment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achiverment_id")
    private int achivermentID;

    @Column(name = "content")
    private String content;


    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Account teacherId;
}
