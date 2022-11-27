package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.repository.TeacherRecordRepository;
import com.example.SWP_1631.service.TeacherRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherRecordServiceImpl implements TeacherRecordService {
    @Autowired
    private TeacherRecordRepository teacherRecordRepository;
}
