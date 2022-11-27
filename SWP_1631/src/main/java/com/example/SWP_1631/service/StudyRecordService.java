package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Clazz;
import com.example.SWP_1631.entity.StudyRecord;

import java.util.List;
import java.util.Optional;

public interface StudyRecordService {

    public StudyRecord getStudyRecordByIdKinderId(Integer id);

    public void save(StudyRecord studyRecord);

    public void deleteByIdClass(Integer userId);


    List<StudyRecord> getStudyRecordByIdClassId(int clazzId);


}
