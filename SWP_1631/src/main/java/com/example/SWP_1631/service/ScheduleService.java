package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Schedule;
import com.example.SWP_1631.entity.ScheduleDetails;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;

public interface ScheduleService {
    LinkedHashMap<LocalDate, String> getAllWeeksInYear(int i);

    public String firstDayOfWeek(Date date);

    public ScheduleDetails getScheduleDetailsByClassDate(int classid, String date);

    public void save(Schedule schedule);

    public void deleteById(Integer id);
}
