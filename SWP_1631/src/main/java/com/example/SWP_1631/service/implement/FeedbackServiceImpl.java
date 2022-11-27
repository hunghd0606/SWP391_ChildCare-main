package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Feedback;
import com.example.SWP_1631.repository.FeedbackRepository;
import com.example.SWP_1631.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback getFeedbackByDate(Date dateFormat) {
        return feedbackRepository.getFeedbackByDate(dateFormat);
    }

    @Override
    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}
