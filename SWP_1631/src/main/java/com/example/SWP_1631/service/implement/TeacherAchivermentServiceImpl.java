package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.repository.TeacherAchivermentRepository;
import com.example.SWP_1631.service.TeacherAchivermentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherAchivermentServiceImpl implements TeacherAchivermentService {
    @Autowired
    private TeacherAchivermentRepository teacherAchivermentRepository;
}
