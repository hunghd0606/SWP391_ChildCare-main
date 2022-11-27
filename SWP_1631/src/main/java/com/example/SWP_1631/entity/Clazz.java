package com.example.SWP_1631.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "Class")
public class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int clazzId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "grade")
    private int grade;

    @Column(name = "class_description")
    private String classDescription;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Account account;
}
