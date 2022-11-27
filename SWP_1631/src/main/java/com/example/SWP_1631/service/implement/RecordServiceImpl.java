package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.repository.RecordRepository;
import com.example.SWP_1631.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;
}
