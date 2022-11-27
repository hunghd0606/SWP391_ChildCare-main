package com.example.SWP_1631.controller;

import com.example.SWP_1631.entity.*;
import com.example.SWP_1631.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parents")
public class ParentController {
    @Autowired
    private KindergartnerService kindergartnerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyRecordService studyRecordService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ActivityService activityService;


    @RequestMapping("/ParentsProfile")
    public String profile(Model model, HttpSession session) {
        Account accSe = (Account) session.getAttribute("acc");
        Optional<Account> parentsFound = accountService.getAccount(accSe.getAccountId());
        parentsFound.ifPresent(parent -> model.addAttribute("Parents", parent));
        List<Kindergartner> listKinder = kindergartnerService.getListKinderByIdParent(parentsFound.get().getAccountId()); // lấy danh sách trẻ em của phụ huynh đó
        boolean isKin = true;
        if (listKinder.size() == 0) {
            isKin = false;
        }
        session.setAttribute("isKin", isKin);
        return "Parents/ParentProfile";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String viewSearch(HttpServletRequest request, Model model) {
        if (request.getParameter("search") == null) {
            return "redirect:/admin/";
        }
        String search = request.getParameter("search").trim();
        search = search.replaceAll("\\s\\s+", " ").trim();
        List<Account> listAcc = accountService.getListAccount();
        List<Role> listRo = roleService.getAllRole();
        model.addAttribute("Parents", listAcc);
        model.addAttribute("listR", listRo);
        return "Parents/ParentProfile";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") Integer userId, Model model) {
        Optional<Account> userEdit = accountService.getAccount(userId);
        userEdit.ifPresent(user -> model.addAttribute("Parents", user));
        List<Role> listRo = roleService.getAllRole();
        model.addAttribute("listR", listRo);
        return "Parents/parentUpdateProfile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("id") Integer userId, HttpServletRequest res) throws Exception {
        Optional<Account> ac2 = accountService.getAccount(userId);
        Account ac = new Account();
        if (ac2.isPresent()) {
            ac.setRole(ac2.get().getRole());
            ac.setPassword(ac2.get().getPassword());
        }
        ac.setFirstName(res.getParameter("txtFirstName"));
        ac.setLastName(res.getParameter("txtLastName"));
        boolean gen = res.getParameter("gender").equals("1");
        ac.setGender(gen);
        ac.setEmail(res.getParameter("txtEmail"));
        ac.setDob(res.getParameter("dob"));
        ac.setPhoneNumber(res.getParameter("txtPhone"));
        ac.setAddress(res.getParameter("ttAddress"));
        ac.setImg(res.getParameter("txtImg"));
        ac.setAccountId(userId);
        System.out.println(ac.toString());
        accountService.update(ac);
        return "redirect:/parents/ParentsProfile";
    }

    // Dức
    @RequestMapping("/childprofile")
    public String showchild(Model model, HttpSession session, HttpServletRequest res) {
        Account accSession = (Account) session.getAttribute("acc");
        Optional<Account> acc = accountService.getAccount(accSession.getAccountId()); // lấy account
        acc.ifPresent(ac -> model.addAttribute("Account", ac)); // set attribute
        if (acc.isPresent()) {
            List<Kindergartner> listKinder = kindergartnerService.getListKinderByIdParent(acc.get().getAccountId()); // lấy danh sách trẻ em của phụ huynh đó

            if (listKinder.size() == 0) { // ko có đứa trẻ nào thì bị đẩy về trang thông tin phụ huynh
                return "redirect:/parents/ParentsProfile";
            } else {
                boolean isKin = true;
                session.setAttribute("isKin", isKin);
            }

            model.addAttribute("listKinder", listKinder); // set attribute
            if (res.getParameter("mainchildid") != null) {
                int index = Integer.parseInt(res.getParameter("mainchildid")); // id của trẻ em muốn get data
                Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(index); // lấy thông tin của đứa trẻ
                kindergartner.ifPresent(user -> model.addAttribute("Kinder", user));  // set attribute
                StudyRecord studyRecord = studyRecordService.getStudyRecordByIdKinderId(index); // lấy thông tin việc học của trẻ theo id trẻ
                model.addAttribute("studyRecord", studyRecord); // set attribute
                model.addAttribute("mainchildid", index); // set attribute id của trẻ
            } else {
                if (res.getParameter("idKinSearch") != null) {
                    int index = Integer.parseInt(res.getParameter("idKinSearch")); // id của trẻ em muốn get data
                    Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(index); // lấy thông tin của đứa trẻ
                    kindergartner.ifPresent(user -> model.addAttribute("Kinder", user));  // set attribute
                    StudyRecord studyRecord = studyRecordService.getStudyRecordByIdKinderId(index); // lấy thông tin việc học của trẻ theo id trẻ
                    model.addAttribute("studyRecord", studyRecord); // set attribute
                    model.addAttribute("idKinSearch", index); // set attribute id của trẻ
                    model.addAttribute("mainchildid", index); // set attribute id của trẻ
                }else {
                    if (listKinder.size() > 0) {
                        model.addAttribute("Kinder", listKinder.get(0));
                        StudyRecord studyRecord = studyRecordService.getStudyRecordByIdKinderId(listKinder.get(0).getKinderId());
                        model.addAttribute("studyRecord", studyRecord);
                        model.addAttribute("mainchildid", listKinder.get(0).getKinderId());
                    }
                }

            }

        }
        return "Parents/childprofile";
    }

    @RequestMapping("/childdetailAttdence")
    public String showchildDetail(Model model, HttpSession session, HttpServletRequest res) {
        Account accSession = (Account) session.getAttribute("acc");
        Optional<Account> acc = accountService.getAccount(accSession.getAccountId());
        acc.ifPresent(ac -> model.addAttribute("Account", ac));
        if (acc.isPresent()) {
            List<Kindergartner> listKinder = kindergartnerService.getListKinderByIdParent(acc.get().getAccountId());
            model.addAttribute("listKinder", listKinder);
            if (res.getParameter("mainchildid") != null) {
                int index = Integer.parseInt(res.getParameter("mainchildid"));
                Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(index);
                kindergartner.ifPresent(user -> model.addAttribute("Kinder", user));
                model.addAttribute("mainchildid", index);
                List<Attendance> listAttendance = attendanceService.getAllAttendanceByIdKinder(index);
                model.addAttribute("listAttendance", listAttendance);
            } else {
                if (res.getParameter("idKinSearch") != null) {
                    int index = Integer.parseInt(res.getParameter("idKinSearch"));
                    Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(index);
                    kindergartner.ifPresent(user -> model.addAttribute("Kinder", user));
                    model.addAttribute("mainchildid", index);
                    model.addAttribute("idKinSearch", index);
                    List<Attendance> listAttendance = attendanceService.getAllAttendanceByIdKinder(index);
                    model.addAttribute("listAttendance", listAttendance);
                }else {
                    if (listKinder.size() > 0) {
                        model.addAttribute("Kinder", listKinder.get(0));
                        model.addAttribute("mainchildid", listKinder.get(0).getKinderId());
                        List<Attendance> listAttendance = attendanceService.getAllAttendanceByIdKinder(listKinder.get(0).getKinderId());
                        model.addAttribute("listAttendance", listAttendance);
                    }
                }

            }

        }
        return "Parents/childdetailAttdence";
    }

    @RequestMapping(value = "/editChild", method = RequestMethod.GET)
    public String editchild(@RequestParam("id") Integer userId, Model model, HttpSession session) {
        Account accSession = (Account) session.getAttribute("acc");
        Optional<Account> acc = accountService.getAccount(accSession.getAccountId());
        acc.ifPresent(ac -> model.addAttribute("Account", ac));
        if (acc.isPresent()) {
            int index = userId;
            Optional<Kindergartner> kindergartner = kindergartnerService.getKindergartnerById(index);
            kindergartner.ifPresent(user -> model.addAttribute("Kinder", user));
            StudyRecord studyRecord = studyRecordService.getStudyRecordByIdKinderId(index);
            model.addAttribute("studyRecord", studyRecord);
            model.addAttribute("mainchildid", index);
        }
        return "Parents/childupdateprofile";
    }

    @RequestMapping(value = "/updateChild", method = RequestMethod.POST)
    public String updateC(@RequestParam("id") Integer userId, HttpServletRequest res, HttpSession session) {
        if(userId != null){
            Kindergartner kindergartner = new Kindergartner();
            Optional<Kindergartner> opKin = kindergartnerService.getKindergartnerById(userId);
            if (opKin.isPresent()) {
                kindergartner = opKin.get();
            }
            kindergartner.setFirstName(res.getParameter("txtFirstName"));
            kindergartner.setLastName(res.getParameter("txtLastName"));
            boolean gen = res.getParameter("gender").equals("1");
            kindergartner.setGender(gen);
            try {
                kindergartner.setDob(res.getParameter("dob"));
            } catch (Exception e) {

            }
            kindergartnerService.update(kindergartner);
            return "redirect:/parents/childprofile?mainchildid="+userId;
        }

        return "redirect:/parents/editChild";
    }

    @RequestMapping("/viewTimeTable")
    public String viewTimeTable(Model model, HttpSession session, HttpServletRequest request) {
        Account accSession = (Account) session.getAttribute("acc");
        List<Kindergartner> listKinder = kindergartnerService.getListKinderByIdParent(accSession.getAccountId());
        model.addAttribute("listKinder", listKinder);
        List<Clazz> listClass = clazzService.getAllClazz();
        StudyRecord studyRecord = new StudyRecord();
        String classid_raw = request.getParameter("cid");
        int classid = 1;
        if (classid_raw == null) {
            if(listKinder.size()>0){
                studyRecord = studyRecordService.getStudyRecordByIdKinderId(listKinder.get(0).getKinderId());
                classid = studyRecord.getClassId().getClazzId();
                model.addAttribute("cid_raw", listKinder.get(0).getKinderId());
                System.err.println("A: "+listKinder.get(0).getKinderId());
            }

        } else {
            try {
                studyRecord = studyRecordService.getStudyRecordByIdKinderId(Integer.parseInt(classid_raw));
                classid = studyRecord.getClassId().getClazzId();
                model.addAttribute("cid_raw", Integer.parseInt(classid_raw));
                System.err.println("B: "+Integer.parseInt(classid_raw));
            } catch (Exception e) {
            }
        }


        LinkedHashMap<LocalDate, String> allWeeks = scheduleService.getAllWeeksInYear(2022);
        model.addAttribute("weeks", allWeeks);
        List<Activity> listActivity = activityService.getAll();
        model.addAttribute("activity", listActivity);

        //**
        String date = request.getParameter("recentMonday");

        if (session.getAttribute("dateSessionViewSchedluAdmin") != null) {
//            date = String.valueOf(session.getAttribute("dateSessionViewSchedluAdmin"));

            System.err.println("dateSe " + session.getAttribute("dateSessionViewSchedluAdmin"));
        }
        String date2 = date;

        if (date != null) {
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date d = sdf1.parse(date);

                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                date = sdf2.format(d);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.err.println("date1 " + date);
        } else {
            date = scheduleService.firstDayOfWeek(new Date());
            System.err.println("date2 " + date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date3 = "";
            try {
                Date d = sdf.parse(date);
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                date3 = sdf.format(d);
            } catch (Exception e) {
                System.out.println(e);
            }
            session.setAttribute("recentMonday", date3);
        }
        ScheduleDetails sde = scheduleService.getScheduleDetailsByClassDate(classid, date);

//        System.out.println("*********************");
//        System.out.println(sde.getScheduleMap());
        model.addAttribute("scheduleDetails", sde);
        int[] loop = {0, 1, 2, 3, 4, 5, 6};
        model.addAttribute("loop", loop);
        //return true date
        String action = request.getParameter("action");
        if (action == null) {
            LocalDate now = LocalDate.now();
            LocalDate firstDayOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            System.err.println(firstDayOfWeek);
            model.addAttribute("firstMonday", firstDayOfWeek);
        } else {

            model.addAttribute("recentMonday", date2);
//            session.setAttribute("recentMonday", date);

        }

        return "Parents/parentsViewTimeTable";
    }

    // n.a
    @GetMapping("/child")
    public String view(Model model) {
        List<Clazz> list = clazzService.getAllClazz();
        model.addAttribute("ListClazz", list);
        return "Parents/childregister";
    }

    @RequestMapping(value = "/registerChild", method = RequestMethod.POST)
    public String update(HttpServletRequest res, HttpSession session) throws Exception {
        Kindergartner kindergartner = new Kindergartner();
        Account accSession = (Account) session.getAttribute("acc");
        kindergartner.setAccount(accSession);
        kindergartner.setFirstName(res.getParameter("ChildFirstName"));
        kindergartner.setLastName(res.getParameter("ChildLastName"));
        boolean gen = !res.getParameter("flexRadioDefault").equals("female");
        kindergartner.setGender(gen);
        kindergartner.setDob(res.getParameter("dob"));
        kindergartnerService.save(kindergartner);
        StudyRecord studyRecord = new StudyRecord();

        Optional<Clazz> clazz = clazzService.getById(Integer.parseInt(res.getParameter("register_classid")));
        if (clazz.isPresent()) {
            studyRecord.setClassId(clazz.get());
            studyRecord.setKinderId(kindergartner);
            studyRecord.setStudyYear(java.time.LocalDate.now().getYear());
        }
        studyRecordService.save(studyRecord);
        boolean isKin = true;
        session.setAttribute("isKin", isKin);
        return "redirect:/parents/child";
    }
}
