package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Clazz;
import com.example.SWP_1631.entity.StudyRecord;
import com.example.SWP_1631.repository.StudyRecordRepository;
import com.example.SWP_1631.service.StudyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudyRecordServiceImpl implements StudyRecordService {
    @Autowired
    private StudyRecordRepository studyRecordRepository;

    @Override
    public StudyRecord getStudyRecordByIdKinderId(Integer id) {
        return studyRecordRepository.getStudyRecordByIdKinderId(id);
    }

    @Override
    public void save(StudyRecord studyRecord) {
        studyRecordRepository.save(studyRecord);
    }

    @Override
    @Transactional
    public void deleteByIdClass(Integer userId) {
        studyRecordRepository.deleteByIdClazz(userId);
    }

    @Override
    public List<StudyRecord> getStudyRecordByIdClassId(int clazzId) {
        return studyRecordRepository.getStudyRecordByIdClassId(clazzId);
    }


}
