package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.repository.CriteriaRepository;
import com.example.SWP_1631.service.CriteriaService;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriteriaServiceImpl implements CriteriaService {
    @Autowired
    private CriteriaRepository critRes;
}
