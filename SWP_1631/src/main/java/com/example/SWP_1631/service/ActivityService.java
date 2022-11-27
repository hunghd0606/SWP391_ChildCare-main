package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    public List<Activity> getAll();

    public Optional<Activity> getActivityById(Integer id);
}
