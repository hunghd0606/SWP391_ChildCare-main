package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Schedule;
import com.example.SWP_1631.entity.ScheduleDetails;
import com.example.SWP_1631.repository.ScheduleRepository;
import com.example.SWP_1631.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    public LocalDate getFirstMondayOfMonthYear(int month, int year) {
        double t1, t2;
        t1 = System.currentTimeMillis();
        LocalDate now = LocalDate.of(year, month, 1);
        LocalDate firstMonday = now.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        t2 = System.currentTimeMillis();
        return firstMonday;
    }

    @Override
    public LinkedHashMap<LocalDate, String> getAllWeeksInYear(int year) {
        double t1, t2;
        t1 = System.currentTimeMillis();
        LinkedHashMap map = new LinkedHashMap<>();
        String t = "";
        LocalDate firstMonday = getFirstMondayOfMonthYear(1, year);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            LocalDate lastSunday = firstMonday.plusDays(6);
            t = firstMonday.toString() + " To " + lastSunday.toString();
            t = fmt.format(firstMonday) + " To " + fmt.format(lastSunday);
            map.put(firstMonday, t);
            firstMonday = lastSunday.plusDays(1);
            if (firstMonday.getYear() > year) {
                break;
            }
        }
        t2 = System.currentTimeMillis();
        return map;
    }

    @Override
    public String firstDayOfWeek(Date date) {
        double t1, t2;
        t1 = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String d = sdf.format(calendar.getTime());
        t2 = System.currentTimeMillis();
        return d;
    }

    private static Date findNextDay(Date date) {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }

    public List<Date> getWeekDay(String date) {
        List<Date> listDate = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse(date);
            for (int i = 0; i < 7; i++) {
                listDate.add(d);
                d = findNextDay(d);
            }
        } catch (ParseException e) {

        }
        return listDate;
    }

    public String convertDate(String date) {
        //22/06/20022
        String result = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d = sdf1.parse(date);
            result = sdf2.format(d);
        } catch (ParseException e) {

        }

        return result;
    }

    public List<Schedule> getAllSchedulesByClassDate(int classID, String date) {
        List<Schedule> list = new ArrayList<>();
        String newDate = convertDate(date);
        for (int i = 1; i <= 7; i++) {
            Schedule s = getScheduleByClassDateSlot(classID, newDate, i);
            list.add(s);
        }
        return list;
    }

    private Schedule getScheduleByClassDateSlot(int classID, String newDate, int slot_id) {
        return scheduleRepository.getScheduleByClassDateSlot(classID, newDate, slot_id);
    }

    @Override
    public ScheduleDetails getScheduleDetailsByClassDate(int classID, String date) {
        ScheduleDetails sde = new ScheduleDetails();
        LinkedHashMap map = new LinkedHashMap<Date, List<Schedule>>();
        sde.setClassID(classID);
        try {
            List<Date> listDate = getWeekDay(date);

            listDate.forEach(d -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dd = sdf.format(d);
                List<Schedule> list = getAllSchedulesByClassDate(classID, dd);
                map.put(d, list);
            });
            System.out.println(map);
            sde.setScheduleMap(map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return sde;
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteById(Integer id) {
        scheduleRepository.deleteById(id);
    }
}
