package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Activity;
import com.example.SWP_1631.repository.ActivityRepository;
import com.example.SWP_1631.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository accRes;

    @Override
    public List<Activity> getAll() {
        return accRes.findAll();
    }

    @Override
    public Optional<Activity> getActivityById(Integer id) {
        return accRes.findById(id);
    }


}
