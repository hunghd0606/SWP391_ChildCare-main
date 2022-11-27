package com.example.SWP_1631.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class ScheduleDetails {
    private int classID;
    //hashmap date, list(Schedule)
    private LinkedHashMap<Date, List<Schedule>> scheduleMap;

    public ScheduleDetails() {
    }

    public ScheduleDetails(int classID, LinkedHashMap<Date, List<Schedule>> scheduleMap) {
        this.classID = classID;
        this.scheduleMap = scheduleMap;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public LinkedHashMap<Date, List<Schedule>> getScheduleMap() {
        return scheduleMap;
    }

    public void setScheduleMap(LinkedHashMap<Date, List<Schedule>> scheduleMap) {
        this.scheduleMap = scheduleMap;
    }
}
