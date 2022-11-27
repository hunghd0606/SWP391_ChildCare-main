package com.example.SWP_1631.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "teacher_record")
public class TeacherRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int recordId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Account teacherId;

    @Column(name = "description")
    private int description;
}