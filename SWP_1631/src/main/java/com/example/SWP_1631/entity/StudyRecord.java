package com.example.SWP_1631.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "study_record")
public class StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_record_id")
    private int recordId;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Clazz classId;

    @ManyToOne
    @JoinColumn(name = "kinder_id")
    private Kindergartner kinderId;

    @Column(name = "study_year")
    private int studyYear;

    @Override
    public String toString() {
        return "StudyRecord{" +
                "recordId=" + recordId +
                ", classId=" + classId +
                ", kinderId=" + kinderId +
                ", studyYear=" + studyYear +
                '}';
    }
}
