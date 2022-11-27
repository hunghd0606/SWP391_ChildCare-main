package com.example.SWP_1631;

import com.example.SWP_1631.entity.*;
import com.example.SWP_1631.service.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Swp1631ApplicationTests {
    @Autowired
    private AccountService accountService;
    @Autowired
    private KindergartnerService kindergartnerService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SlotService slotService;
    @Autowired
    private StudyRecordService studyRecordService;

    //1
    @Test
    void contextLoadsFirstNamById() {
        Account acc = new Account();
        acc.setFirstName("Jonah");
        Optional<Account> acc1 = accountService.getAccount(8);
        acc1.ifPresent(use -> Assert.assertEquals(true, use.getFirstName().equals(acc.getFirstName())));
    }

    //2
    @Test
    void contextLoadsGmailById() {
        Account acc = new Account();
        acc.setEmail("jonah@gmail.com");
        Optional<Account> acc1 = accountService.getAccount(8);
        acc1.ifPresent(use -> Assert.assertEquals(true, use.getEmail().equals(acc.getEmail())));
    }

    //3
    @Test
    void contextLoadsPhoneById() {
        Account acc = new Account();
        acc.setPhoneNumber("0123456789");
        Optional<Account> acc1 = accountService.getAccount(8);
        acc1.ifPresent(use -> Assert.assertEquals(true, use.getPhoneNumber().equals(acc.getPhoneNumber())));
    }

    //4
    @Test
    void contextLoadsAddressById() {
        Account acc = new Account();
        acc.setAddress("Russia");
        Optional<Account> acc1 = accountService.getAccount(8);
        acc1.ifPresent(use -> Assert.assertEquals(true, use.getAddress().equals(acc.getAddress())));
    }

    //5
    @Test
    void contextLoadsKinderNameById() {
        Kindergartner kin = new Kindergartner();
        kin.setFirstName("Landon");
        Optional<Kindergartner> kin1 = kindergartnerService.getKindergartnerById(11);
        kin1.ifPresent(use -> Assert.assertEquals(true, use.getFirstName().equals(kin.getFirstName())));
    }

    //6
    @Test
    void contextLoadsKinderLastNameById() {
        Kindergartner kin = new Kindergartner();
        kin.setLastName("Parr");
        Optional<Kindergartner> kin1 = kindergartnerService.getKindergartnerById(11);
        kin1.ifPresent(use -> Assert.assertEquals(true, use.getLastName().equals(kin.getLastName())));
    }

    //7
    @Test
    void contextLoadsKinderDob() {
        Kindergartner kin = new Kindergartner();
        try {
            kin.setDob("2020-05-06");
            Optional<Kindergartner> kin1 = kindergartnerService.getKindergartnerById(11);
            kin1.ifPresent(use -> Assert.assertEquals(true, use.getDob().equals(kin.getDob())));
        } catch (Exception e) {

        }

    }

    //8
    @Test
    void contextTestKinderByParentId() {
        Kindergartner kin = new Kindergartner();
        List<Kindergartner> kin1 = kindergartnerService.getListKinderByIdParent(8);
        kin.setKinderId(11);
        assertEquals(kin.getKinderId(), kin1.get(0).getKinderId());
    }

    //9
    @Test
    void contextTestKinderFirstNameByParentId() {
        Kindergartner kin = new Kindergartner();
        List<Kindergartner> kin1 = kindergartnerService.getListKinderByIdParent(8);
        kin.setFirstName("Landon");
        assertEquals(kin.getFirstName(), kin1.get(0).getFirstName());
    }

    //10
    @Test
    void contextTestKinderLastNameByParentId() {
        Kindergartner kin = new Kindergartner();
        List<Kindergartner> kin1 = kindergartnerService.getListKinderByIdParent(8);
        kin.setLastName("Parr");
        assertEquals(kin.getLastName(), kin1.get(0).getLastName());
    }

    //11
    @Test
    void contextLoadsKinderDobByParent() {
        Kindergartner kin = new Kindergartner();
        try {
            kin.setDob("2020-05-06");
            List<Kindergartner> kin1 = kindergartnerService.getListKinderByIdParent(8);
            assertEquals(kin.getDob(), kin1.get(0).getDob());
        } catch (Exception e) {
        }
    }

    //11
    @Test
    void getActivityDescription() {
        Activity act = new Activity();
        act.setDescription("Have breakfast");
        Optional<Activity> activity = activityService.getActivityById(1);
        assertEquals(act.getDescription(), activity.get().getDescription());
    }

    //12
    @Test
    void getActivityName() {
        Activity act = new Activity();
        act.setName("Breakfast");
        Optional<Activity> activity = activityService.getActivityById(1);
        assertEquals(act.getName(), activity.get().getName());
    }

    //13
    @Test
    void getClassNameById() {
        Clazz clazz = new Clazz();
        clazz.setClassName("SE1609");
        Optional<Clazz> clazz1 = clazzService.getById(2);
        assertEquals(clazz.getClassName(), clazz1.get().getClassName());
    }

    //14
    @Test
    void getGradeById() {
        Clazz clazz = new Clazz();
        clazz.setGrade(3);
        Optional<Clazz> clazz1 = clazzService.getById(2);
        assertEquals(clazz.getGrade(), clazz1.get().getGrade());
    }

    //15
    @Test
    void getClassDescriptionById() {
        Clazz clazz = new Clazz();
        clazz.setClassDescription("It was great");
        Optional<Clazz> clazz1 = clazzService.getById(2);
        assertEquals(clazz.getClassDescription(), clazz1.get().getClassDescription());
    }

    //16
    @Test
    void getStartHourById() {
        Slot slot = new Slot();
        slot.setStartHour("7.0");
        Optional<Slot> slot1 = slotService.getSlotById(1);
        assertEquals(slot.getStartHour(), slot1.get().getStartHour());
    }

    //17
    @Test
    void getEndHourById() {
        Slot slot = new Slot();
        slot.setEndHour("8.0");
        Optional<Slot> slot1 = slotService.getSlotById(1);
        assertEquals(slot.getEndHour(), slot1.get().getEndHour());
    }

    //18
    @Test
    void getYearByRecord() {
        StudyRecord std = new StudyRecord();
        std.setStudyYear(2022);
        StudyRecord std1 = studyRecordService.getStudyRecordByIdKinderId(11);
        assertEquals(std.getStudyYear(), std1.getStudyYear());
    }

}
