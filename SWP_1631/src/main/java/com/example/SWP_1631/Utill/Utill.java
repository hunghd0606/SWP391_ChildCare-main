package com.example.SWP_1631.Utill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Utill {
    public String RandNum(int Nnum) {
        int min = 0;
        int max = 10;
        String num = "";
        for (int i = 0; i < Nnum; i++) {
            num += String.valueOf((int) (Math.random() * (max - min + 1) + min));
        }
        return num;
    }

    public String RandomStringg(int s) {
        String randomString = "";
        Random random = new Random();
        while (randomString.length() != s) {
            char randomizedCharacter = (char) (random.nextInt(26) + 'a');
            randomString += randomizedCharacter;
        }
        return randomString;
    }

    public List<String> getAllDateOfMonth(int month) {
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            list.add(df.format(cal.getTime()));
        }
        return list;
    }
}
