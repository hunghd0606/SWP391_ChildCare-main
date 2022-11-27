package com.example.SWP_1631.controller;

import com.example.SWP_1631.Utill.Utill;
import com.example.SWP_1631.entity.*;
import com.example.SWP_1631.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleSer;

    @Autowired
    private StudyRecordService studyRecordService;

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private KindergartnerService kindergartnerService;
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("/checkAttendence")
    public String atten(Model model) {
        return "teacher/checkAttendence";
    }

    private Utill utill = new Utill();

    @GetMapping("/")
    public String profile(Model model, HttpSession session) {
        Account accSe = (Account) session.getAttribute("acc");
        Optional<Account> teacherFound = accountService.getAccount(accSe.getAccountId());
        teacherFound.ifPresent(teacher -> model.addAttribute("Teacher", teacher));
        return "teacher/teacherProfile";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String update(@RequestParam("id") Integer id, Model model) {
        Optional<Account> teacherFound = accountService.getAccount(id);
        teacherFound.ifPresent(teacher -> model.addAttribute("Teacher", teacher));
        return "teacher/teacherUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("id") Integer userId, HttpServletRequest res) throws Exception {
        Optional<Account> ac2 = accountService.getAccount(userId);
        Account ac = new Account();
        if (ac2.isPresent()) {
            ac.setPassword(ac2.get().getPassword());
            ac.setRole(ac2.get().getRole());
            ac.setImg(ac2.get().getImg());
        }
        ac.setFirstName(res.getParameter("txtFirstName"));
        ac.setLastName(res.getParameter("txtLastName"));
        boolean gen = !res.getParameter("gender").equals("female");
        ac.setGender(gen);
        ac.setEmail(res.getParameter("txtEmail"));
        ac.setDob(res.getParameter("dob"));
        ac.setPhoneNumber(res.getParameter("txtPhone"));
        ac.setAddress(res.getParameter("ttAddress"));
        ac.setAccountId(userId);
        System.out.println(ac);
        accountService.update(ac);
        return "redirect:/teacher/";
    }

    @GetMapping("/homeTeacher")
    public String homeTeacher(Model model, HttpSession session, HttpServletRequest res) {
        Account accSe = (Account) session.getAttribute("acc");
        String checkindate = LocalDate.now().toString(); // lấy date hiện tại
        if (res.getParameter("checkindate") != null) {
            checkindate = res.getParameter("checkindate");
        }
        Clazz clazz = clazzService.getClazzByIdAccount(accSe.getAccountId()); // lấy các class do acc đó quản lý
        List<StudyRecord> listStudyRecord = studyRecordService.getStudyRecordByIdClassId(clazz.getClazzId()); // tạo list StudyRecord
        if (listStudyRecord.isEmpty()) {
            return "redirect:/teacher/";
        }
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        for (StudyRecord kinderRecordStudy : listStudyRecord) {

            map.put(kinderRecordStudy.getKinderId().getKinderId(), 0);
        }
        List<Attendance> attendances = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date e = sdf.parse(checkindate);
            attendances = attendanceService.getAllAttendanceOfInputDay(e);
        } catch (Exception e) {

        }
        if (!attendances.isEmpty()) {
            for (Attendance a : attendances) {
                if (a.getStatus() != 0) {
                    map.replace(a.getStudentId().getKinderId(), 1);
                }
            }
        }
        model.addAttribute("clazz", clazz.getClassName());
        model.addAttribute("studentMap", map);
        model.addAttribute("checkindate", checkindate);
        model.addAttribute("listStudyRecord", listStudyRecord);
        model.addAttribute("isPast", !checkindate.equalsIgnoreCase(LocalDate.now().toString()));
        model.addAttribute("present_kids", attendances);
        return "teacher/homeTeacher";
    }

    @RequestMapping(value = "/UpdateAttendance", method = RequestMethod.POST)
    public String updatetest(@RequestParam("checkindate") String checkdate, Model model, HttpServletRequest res, HttpSession session) {
        Account accSe = (Account) session.getAttribute("acc");
        String checkindate = LocalDate.now().toString(); // lấy date hiện tại
        if (checkdate != null) {
            checkindate = checkdate;
            System.err.println("In" + checkindate);
        }
        Clazz clazz = clazzService.getClazzByIdAccount(accSe.getAccountId()); // lấy các class do acc đó quản lý
        List<StudyRecord> listStudyRecord = studyRecordService.getStudyRecordByIdClassId(clazz.getClazzId()); // tạo list StudyRecord

        for (StudyRecord list : listStudyRecord) {
            Attendance attendance = new Attendance();
            attendance.setTeacherId(accSe);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateFormat = sdf.parse(checkindate);
                Optional<Attendance> attendanceOptional = attendanceService.getAttendanceByStudentIdAndDateAndTeacherId(list.getKinderId().getKinderId(), dateFormat, accSe.getAccountId());
                if (attendanceOptional != null && attendanceOptional.isPresent()) {
                    attendance = attendanceOptional.get();
                    try {
                        attendance.checkDate(checkindate);
                    } catch (Exception e) {
                    }
                    attendance.setStatus(Integer.parseInt(res.getParameter("gen" + list.getKinderId().getKinderId())));
                } else {
                    try {
                        attendance.checkDate(checkindate);
                    } catch (Exception e) {
                    }
                    attendance.setStudentId(list.getKinderId());
                    attendance.setStatus(Integer.parseInt(res.getParameter("gen" + list.getKinderId().getKinderId())));
                    attendance.checkDate(LocalDate.now().toString());
                }
                attendanceService.save(attendance);
            } catch (Exception e) {

            }

        }
        return "redirect:/teacher/homeTeacher";
    }

    @RequestMapping(value = "/searchKinder", method = RequestMethod.GET)
    public String searchKinder(@RequestParam("id") Integer id, Model model, HttpSession session) {
        Account accSe = (Account) session.getAttribute("acc");
        Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(id);
        kindergartner.ifPresent(user -> model.addAttribute("kindergartner", user));
        Clazz clazz = clazzService.getClazzByIdAccount(accSe.getAccountId());
        model.addAttribute("accParent", accSe);
        model.addAttribute("clazz", clazz);
        return "teacher/viewStudent";
    }

    @RequestMapping(value = "/feedbackStudent", method = RequestMethod.GET)
    public String feedbackStudent(@RequestParam("id") Integer id, Model model, HttpSession session) {
        Account accSe = (Account) session.getAttribute("acc");
        Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(id);
        kindergartner.ifPresent(user -> model.addAttribute("kindergartner", user));
        Clazz clazz = clazzService.getClazzByIdAccount(accSe.getAccountId());
        model.addAttribute("accParent", accSe);
        model.addAttribute("clazz", clazz);
        return "teacher/feedbackStudent";
    }

    @RequestMapping(value = "/addFeedbackStudent", method = RequestMethod.POST)
    public String addFeedbackStudent(@RequestParam("id") Integer id, Model model, HttpSession session, HttpServletRequest res) {
        Account accSe = (Account) session.getAttribute("acc");
        Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(id);
//        feedbackService
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dateNow = LocalDate.now().toString();
            Date dateFormat = sdf.parse(dateNow);
            String fbContent = "";
            if (res.getParameter("fbContent") != null) {
                fbContent = res.getParameter("fbContent").trim();
            }
            double rating = 0.0;
            if (res.getParameter("rating") != null) {
                rating = Double.parseDouble(res.getParameter("rating").trim());
            }
            Feedback feedback = feedbackService.getFeedbackByDate(dateFormat);
            if (feedback != null) {
                feedback.setFbContent(fbContent);
                feedback.setRating(rating);
            } else {
                feedback = new Feedback();
                feedback.setFbContent(fbContent);
                feedback.setRating(rating);
                feedback.setTeacherId(accSe);
                feedback.setDob(dateNow);
                feedback.setKidId(kindergartner.get());
            }
            feedbackService.save(feedback);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/teacher/homeTeacher";
    }

    @RequestMapping(value = "/viewAttendance", method = RequestMethod.GET)
    public String viewAttendance(@RequestParam("id") Integer id, Model model, HttpSession session) {
        Account accSe = (Account) session.getAttribute("acc");
        Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(id);
        kindergartner.ifPresent(user -> model.addAttribute("kindergartner", user));
        Clazz clazz = clazzService.getClazzByIdAccount(accSe.getAccountId());
        int month = LocalDate.now().getMonthValue();
        List<String> listDate = utill.getAllDateOfMonth(month);
        System.err.println("*******listDate: " + listDate);
        List<Attendance> listAttendance = new ArrayList<>();
        listAttendance = attendanceService.getAllAttendanceByIdKinderAndDateFromAndDateTo(id, listDate.get(0), listDate.get(listDate.size() - 1));
        System.err.println("*******listDate A: " + listDate.get(listDate.size() - 1));
        LinkedHashMap<Date, Integer> map = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String a : listDate) {
            try {
                Date dateX = sdf.parse(a);
                map.put(dateX, 0);
                if (listAttendance.size() > 0) {
                    for (Attendance att : listAttendance) {
                        Date dateS = sdf.parse(String.valueOf(att.getDate()));
                        if (dateS.equals(dateX)) {
                            map.replace(dateX, 0, 1);
                            break;
                        }
                    }
                }

            } catch (Exception e) {
            }
        }

        model.addAttribute("listDate", map);
        model.addAttribute("attendance", listAttendance);
        model.addAttribute("accParent", accSe);
        model.addAttribute("clazz", clazz);
        return "teacher/viewCheckAttendace";
    }
}
