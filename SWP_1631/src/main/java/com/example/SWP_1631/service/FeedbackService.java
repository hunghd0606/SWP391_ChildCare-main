package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Feedback;

import java.util.Date;

public interface FeedbackService {
    Feedback getFeedbackByDate(Date dateFormat);

    void save(Feedback feedback);
}
