package com.example.SWP_1631.controller;

import com.example.SWP_1631.Utill.SendMail;
import com.example.SWP_1631.Utill.Utill;
import com.example.SWP_1631.entity.*;
import com.example.SWP_1631.service.AccountService;
import com.example.SWP_1631.service.ActivityService;
import com.example.SWP_1631.service.ClazzService;
import com.example.SWP_1631.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ClazzService clazzService;

    @Autowired
    private ActivityService activityService;
    private Utill utill = new Utill();

//    @RequestMapping("/testHome")
//    public String testHome(Model model, HttpSession session, HttpServletRequest request) {
//        List<Clazz> listClass = clazzService.getAllClazz();
//        String classid_raw = request.getParameter("cid");
//        int classid = 1;
//        if (classid_raw == null) {
//            classid = listClass.get(0).getClazzId();
//
//        } else {
//            try {
//                classid = Integer.parseInt(classid_raw);
//            } catch (Exception e) {
//            }
//        }
//        if (session.getAttribute("cidSession") != null) {
//            int cid = Integer.parseInt(String.valueOf(session.getAttribute("cidSession")).trim());
//            classid = cid;
//            model.addAttribute("cid_raw", classid);
//        } else {
//            try {
//                model.addAttribute("cid_raw", listClass.get(0).getClazzId());
//            } catch (Exception e) {
//
//            }
//        }
//        session.setAttribute("cidSession", classid);
//        LinkedHashMap<LocalDate, String> allWeeks = scheduleService.getAllWeeksInYear(2022);
//        model.addAttribute("weeks", allWeeks);
//
//
//        model.addAttribute("classes", listClass);
//
//        List<Activity> listActivity = activityService.getAll();
//        model.addAttribute("activity", listActivity);
//
//        //**
//        String date = request.getParameter("recentMonday");
//
//        if (session.getAttribute("dateSessionViewSchedluAdmin") != null) {
////            date = String.valueOf(session.getAttribute("dateSessionViewSchedluAdmin"));
//
//            System.err.println("dateSe " + session.getAttribute("dateSessionViewSchedluAdmin"));
//        }
//        String date2 = date;
//
//        if (date != null) {
//            try {
//                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//                Date d = sdf1.parse(date);
//
//                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//                date = sdf2.format(d);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//            System.err.println("date1 " + date);
//        } else {
//            date = scheduleService.firstDayOfWeek(new Date());
//            System.err.println("date2 " + date);
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            String date3 = "";
//            try {
//                Date d = sdf.parse(date);
//                sdf = new SimpleDateFormat("yyyy-MM-dd");
//                date3 = sdf.format(d);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//            session.setAttribute("recentMonday", date3);
//        }
//        ScheduleDetails sde = scheduleService.getScheduleDetailsByClassDate(classid, date);
//
////        System.out.println("*********************");
////        System.out.println(sde.getScheduleMap());
//        model.addAttribute("scheduleDetails", sde);
//        int[] loop = {0, 1, 2, 3, 4, 5, 6};
//        model.addAttribute("loop", loop);
//        //return true date
//        String action = request.getParameter("action");
//        if (action == null) {
//            LocalDate now = LocalDate.now();
//            LocalDate firstDayOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//            System.err.println(firstDayOfWeek);
//            model.addAttribute("firstMonday", firstDayOfWeek);
//        } else {
//
//            model.addAttribute("recentMonday", date2);
////            session.setAttribute("recentMonday", date);
//
//        }
//        return "Parents/parentsViewTimeTable";
//    }

    @RequestMapping("/")
    public String init(Model model, HttpSession session) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }else {
            return "index";
        }

    }

    @RequestMapping("/loginSuccess")
    public String ViewS(HttpSession session) {
        if (session.getAttribute("VaiTro").equals("ROLE_ADMIN")) {
            session.setAttribute("daLogin", "trueA");
            session.setMaxInactiveInterval(60*60*24);
            return "redirect:/admin/";
        }
        if (session.getAttribute("VaiTro").equals("ROLE_PARENT")) {
            session.setAttribute("daLogin", "trueA");
            session.setMaxInactiveInterval(60*60*24);
            return "redirect:/parents/ParentsProfile";
        }
        if (session.getAttribute("VaiTro").equals("ROLE_TEACHER")) {
            session.setAttribute("daLogin", "trueA");
            session.setMaxInactiveInterval(60*60*24);
            return "redirect:/teacher/";
        }
        return "index";
    }

    @RequestMapping("/register")
    public String req(Model model, HttpSession session) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        if (session.getAttribute("forPassHome") != null) {
            session.removeAttribute("forPassHome");
        }
        String s = utill.RandomStringg(20);
        session.setAttribute("registerSession", s);
        session.setMaxInactiveInterval(60 * 10);
        model.addAttribute("accountTam", new Account());
        model.addAttribute("exitEmail", false);
        model.addAttribute("isRegis", false);
        return "register";
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public String createAccount(Model model, HttpSession session, HttpServletRequest res) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        if (res.getParameter("id") != null && res.getParameter("id").equals(session.getAttribute("registerSession"))) {
            Account account = new Account();
            account.setFirstName(res.getParameter("fname"));
            account.setLastName(res.getParameter("lname"));
            boolean gender = res.getParameter("gender").equals("male");
            account.setGender(gender);
            account.setAddress(res.getParameter("address"));
            account.setPhoneNumber(res.getParameter("phone"));
            account.setEmail(res.getParameter("email"));
            try {
                account.setDob(res.getParameter("dob"));
            } catch (Exception e) {
            }
            if (accountService.checkEmailExitInDatabase(account.getEmail())) {
                model.addAttribute("accountTam", account);
                model.addAttribute("exitEmail", true);
                model.addAttribute("isRegis", false);
                return "register";
            } else {
                account.setRole(new Role(2));
                account.setPassword(utill.RandomStringg(6));
                session.setAttribute("accountTamThoiRegister", account);
                String code = utill.RandomStringg(6) + utill.RandNum(2);
                session.setAttribute("codeRegister", code);
                session.setMaxInactiveInterval(60 * 15);
                SendMail sendMail = new SendMail();
                String gen = "Male";
                if (!account.isGender()) {
                    gen = "FeMale";
                }
                String html = "" +
                        "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>AA</title>\n" +
                        "    <style>\n" +
                        "        *{\n" +
                        "            box-sizing: border-box;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        body{\n" +
                        "            background-color: bisque;\n" +
                        "        }\n" +
                        "        .container{\n" +
                        "            margin: 10%;\n" +
                        "            background-color: rgb(223, 205, 171);\n" +
                        "        }\n" +
                        "        .code{\n" +
                        "            width: 20%;\n" +
                        "            margin: 0 10%;\n" +
                        "            background-color: rgb(241, 132, 132);\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"container\"> \n" +
                        "    Hello " + account.getFirstName() + ",<br/>\n" +
                        "    For security purposes, you must enter the code below to verify your account to access<br/>\n" +
                        "    The code will only work for 15 minutes and if you request a new code, this code will stop working.\n" +
                        "    <div class=\"code\">\n" +
                        "        Account verification code:<br/>\n" +
                        "        <h3 style=\"color: red;\">" + code + "</h3>\n" +
                        "    </div>\n" +
                        "    <br/>\n" +
                        "    You can check information below:\n" +
                        "    <table border=\"1\" style=\"\">\n" +
                        "        <tr>\n" +
                        "            <th>First Name</th>\n" +
                        "            <th>Last Name</th>\n" +
                        "            <th>Gender</th>\n" +
                        "            <th>Email</th>\n" +
                        "            <th>Date</th>\n" +
                        "            <th>Phone Number</th>\n" +
                        "            <th>Address</th>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td>" + account.getFirstName() + "</td>\n" +
                        "            <td>" + account.getLastName() + "</td>\n" +
                        "            <td>" + gen + "</td>\n" +
                        "            <td>" + account.getEmail() + "</td>\n" +
                        "            <td>" + account.getDob() + "</td>\n" +
                        "            <td>" + account.getPhoneNumber() + "</td>\n" +
                        "            <td>" + account.getAddress() + "</td>\n" +
                        "        </tr>\n" +
                        "         \n" +
                        "    </table>\n" +
                        "</div>\n" +
                        "    \n" +
                        "</body>\n" +
                        "</html>";
                if (sendMail.isStatus()) {
                    sendMail.sendFuncition(account.getEmail(), code + " is your Confirm Your email!! !", html);
                }
                model.addAttribute("isRegis", true);
                model.addAttribute("accountTam", new Account());
                model.addAttribute("exitEmail", false);
                model.addAttribute("doneRegis", false);
                String s = utill.RandomStringg(15) + utill.RandNum(10);
                session.setAttribute("registerSession", s);
                return "register";
            }

        }
        return "redirect:/home/register";
    }


    @RequestMapping(value = "/checkRegister", method = RequestMethod.POST)
    public String checkRegister(Model model, HttpSession session, HttpServletRequest res) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        if (session.getAttribute("accountTamThoiRegister") != null) {
            if (res.getParameter("id") != null && res.getParameter("id").trim().equals(session.getAttribute("registerSession"))) {
//                codeRegister 101010 là mã code dự phòng kẻo trên trường ko gửi mã về gmail đc do mạng kém
                SendMail sendMail = new SendMail();
                if (res.getParameter("codeRe").equals(session.getAttribute("codeRegister")) || res.getParameter("codeRe").equals(sendMail.getDefaupass())) {
                    Account account = (Account) session.getAttribute("accountTamThoiRegister");
//                    ac.setPassword();
                    String pass = account.getPassword();
                    account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(12)));
                    accountService.save(account); // lưu vào db
                    // xuất từ db ra
                    Account user = accountService.getAccByEmail(account.getEmail());
                    String gen = "Male";
                    if (!account.isGender()) {
                        gen = "FeMale";
                    }
                    String html = "" +
                            "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>AA</title>\n" +
                            "    <style>\n" +
                            "        *{\n" +
                            "            box-sizing: border-box;\n" +
                            "            margin: 0;\n" +
                            "            padding: 0;\n" +
                            "        }\n" +
                            "        body{\n" +
                            "            background-color: bisque;\n" +
                            "        }\n" +
                            "        .container{\n" +
                            "            margin: 10%;\n" +
                            "            background-color: rgb(223, 205, 171);\n" +
                            "        }\n" +
                            "        .code{\n" +
                            "            width: 20%;\n" +
                            "            margin: 0 10%;\n" +
                            "            background-color: rgb(27, 235, 27);\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<div class=\"container\"> \n" +
                            "    Hello,<br/>\n" +
                            "    Welcome to us<br/>\n" +
                            "     \n" +
                            "    <div class=\"code\">\n" +
                            "        Account:  <span style=\"color: rgb(255, 230, 0);\">" + account.getEmail() + "</span><br/>\n" +
                            "        Password:  <span style=\"color: rgb(255, 230, 0);\">" + pass + "</span><br/>\n" +
                            "    </div>\n" +
                            "    <br/>\n" +
                            "    You can check information below:\n" +
                            "    <table border=\"1\" style=\"\">\n" +
                            "        <tr>\n" +
                            "            <th>First Name</th>\n" +
                            "            <th>Last Name</th>\n" +
                            "            <th>Gender</th>\n" +
                            "            <th>Email</th>\n" +
                            "            <th>Date</th>\n" +
                            "            <th>Phone Number</th>\n" +
                            "            <th>Address</th>\n" +
                            "        </tr>\n" +
                            "        <tr>\n" +
                            "            <td>" + account.getFirstName() + "</td>\n" +
                            "            <td>" + account.getLastName() + "</td>\n" +
                            "            <td>" + gen + "</td>\n" +
                            "            <td>" + account.getEmail() + "</td>\n" +
                            "            <td>" + account.getDob() + "</td>\n" +
                            "            <td>" + account.getPhoneNumber() + "</td>\n" +
                            "            <td>" + account.getAddress() + "</td>\n" +
                            "        </tr>\n" +
                            "         \n" +
                            "    </table>\n" +
                            "</div>\n" +
                            "    \n" +
                            "</body>\n" +
                            "</html>";
                    if (sendMail.isStatus()) {
                        sendMail.sendFuncition(account.getEmail(), "Welcome to us", html);
                    } else {
                        System.err.println("Pass: " + pass);
                    }
                    sendMail.sendFuncition(account.getEmail(), "Welcome to us", html);
                    session.removeAttribute("accountTamThoiRegister");
                    session.removeAttribute("codeRegister");
                    session.removeAttribute("registerSession");

                    model.addAttribute("isRegis", true);
                    model.addAttribute("accountTam", new Account());
                    model.addAttribute("exitEmail", false);
                    model.addAttribute("doneRegis", true);
                    return "register";
                } else {
                    model.addAttribute("isRegis", true);
                    model.addAttribute("accountTam", new Account());
                    model.addAttribute("exitEmail", true);
                    model.addAttribute("doneRegis", false);
                    model.addAttribute("codeFalse", res.getParameter("codeRe"));
                    return "register";
                }
            }
        }
        return "redirect:/home/register";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        if (session.getAttribute("accountTamThoiRegister") != null) {
            session.removeAttribute("accountTamThoiRegister");
        }
        if (session.getAttribute("registerSession") != null) {
            session.removeAttribute("registerSession");
        }
        if (session.getAttribute("forPassHome") != null) {
            session.removeAttribute("forPassHome");
        }
        if (request.getParameter("id") != null) {
            model.addAttribute("error", true);
        } else {
            model.addAttribute("error", false);
        }

        return "login";
    }

    @RequestMapping(value = "/nextForgotPass")
    public String nextForgotPass(Model model, HttpSession session) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        session.setAttribute("forPassHome", utill.RandomStringg(10));
        session.setMaxInactiveInterval(60 * 10);
        model.addAttribute("isCheck", false);
        model.addAttribute("accountTam", new Account());
        model.addAttribute("notExit", false);
        return "forgotPassHome";
    }

    @RequestMapping(value = "/forgotPass")
    public String forgotPass(Model model, HttpSession session, HttpServletRequest res) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        if (res.getParameter("id") != null && res.getParameter("id").equals(session.getAttribute("forPassHome"))) {
            Account account = new Account();
            account.setFirstName(res.getParameter("fname"));
            account.setLastName(res.getParameter("lname"));
            boolean gender = res.getParameter("gender").equals("male");
            account.setGender(gender);
            account.setPhoneNumber(res.getParameter("phone"));
            account.setEmail(res.getParameter("email"));
            try {
                account.setDob(res.getParameter("dob"));
            } catch (Exception e) {
            }
            Account accCheck = accountService.getAccByEmail(account.getEmail());
            System.err.println(accCheck.isGender());
            System.err.println(account.isGender());
            if (accCheck != null && accCheck.getEmail().equals(account.getEmail()) && accCheck.getFirstName().equals(account.getFirstName())
                    && accCheck.getLastName().equals(account.getLastName()) && accCheck.isGender() == account.isGender() && accCheck.getPhoneNumber().equals(account.getPhoneNumber())) {
                String gen = "Male";
                if (!account.isGender()) {
                    gen = "FeMale";
                }
                session.setAttribute("accountTamThoi", accCheck);
                String code = utill.RandNum(10);
                session.setAttribute("codeChagePass", code);
                session.setMaxInactiveInterval(60 * 15);
                String html = "" +
                        "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>AA</title>\n" +
                        "    <style>\n" +
                        "        *{\n" +
                        "            box-sizing: border-box;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        body{\n" +
                        "            background-color: bisque;\n" +
                        "        }\n" +
                        "        .container{\n" +
                        "            margin: 10%;\n" +
                        "            background-color: rgb(223, 205, 171);\n" +
                        "        }\n" +
                        "        .code{\n" +
                        "            width: 20%;\n" +
                        "            margin: 0 10%;\n" +
                        "            background-color: rgb(241, 132, 132);\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"container\"> \n" +
                        "    Hello " + account.getFirstName() + ",<br/>\n" +
                        "    We received a request to change your password<br/>\n" +
                        "    <div class=\"code\">\n" +
                        "        Account verification code:<br/>\n" +
                        "        <h3 style=\"color: red;\">" + code + "</h3>\n" +
                        "    </div>\n" +
                        "    <br/>\n" +
                        "    You can check information below:\n" +
                        "    <table border=\"1\" style=\"\">\n" +
                        "        <tr>\n" +
                        "            <th>First Name</th>\n" +
                        "            <th>Last Name</th>\n" +
                        "            <th>Gender</th>\n" +
                        "            <th>Email</th>\n" +
                        "            <th>Date</th>\n" +
                        "            <th>Phone Number</th>\n" +
                        "            <th>Address</th>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td>" + account.getFirstName() + "</td>\n" +
                        "            <td>" + account.getLastName() + "</td>\n" +
                        "            <td>" + gen + "</td>\n" +
                        "            <td>" + account.getEmail() + "</td>\n" +
                        "            <td>" + account.getDob() + "</td>\n" +
                        "            <td>" + account.getPhoneNumber() + "</td>\n" +
                        "            <td>" + account.getAddress() + "</td>\n" +
                        "        </tr>\n" +
                        "         \n" +
                        "    </table>\n" +
                        "</div>\n" +
                        "    \n" +
                        "</body>\n" +
                        "</html>";
                SendMail sendMail = new SendMail();
                if (sendMail.isStatus()) {
                    sendMail.sendFuncition(account.getEmail(), "Welcome to us", html);
                }
                model.addAttribute("isCheck", true);
                session.setAttribute("registerSessionForgotHome", utill.RandomStringg(12));
                session.setMaxInactiveInterval(60 * 15);
                model.addAttribute("doneRegis", false);
                session.removeAttribute("forPassHome");
                return "forgotPassHome";
            } else {
                model.addAttribute("notExit", true);
                model.addAttribute("isCheck", false);
                model.addAttribute("accountTam", account);
                return "forgotPassHome";
            }

        }
        return "redirect:/home/";
    }

    @RequestMapping(value = "/checkForgotPassInHome")
    public String checkForgotPassInHome(Model model, HttpSession session, HttpServletRequest res) {
        if (session.getAttribute("daLogin") != null) {
            return "redirect:/home/loginSuccess";
        }
        if (res.getParameter("id") != null && session.getAttribute("registerSessionForgotHome") != null && res.getParameter("id").equals(session.getAttribute("registerSessionForgotHome"))) {
            SendMail sendMail = new SendMail();
            if (res.getParameter("codeRe").equals(session.getAttribute("codeChagePass")) || res.getParameter("codeRe").equals(sendMail.getDefaupass())) {
                Account account = (Account) session.getAttribute("accountTamThoi");
//                    ac.setPassword();
                String pass = utill.RandNum(10);
                account.setPassword(BCrypt.hashpw(pass, BCrypt.gensalt(12)));
                accountService.save(account); // lưu vào db
                String gen = "Male";
                if (!account.isGender()) {
                    gen = "FeMale";
                }
                String html = "" +
                        "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>AA</title>\n" +
                        "    <style>\n" +
                        "        *{\n" +
                        "            box-sizing: border-box;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "        }\n" +
                        "        body{\n" +
                        "            background-color: bisque;\n" +
                        "        }\n" +
                        "        .container{\n" +
                        "            margin: 10%;\n" +
                        "            background-color: rgb(223, 205, 171);\n" +
                        "        }\n" +
                        "        .code{\n" +
                        "            width: 20%;\n" +
                        "            margin: 0 10%;\n" +
                        "            background-color: rgb(27, 235, 27);\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"container\"> \n" +
                        "    Hello,<br/>\n" +
                        "    New password<br/>\n" +
                        "     \n" +
                        "    <div class=\"code\">\n" +
                        "        Account:  <span style=\"color: rgb(255, 230, 0);\">" + account.getEmail() + "</span><br/>\n" +
                        "        Password:  <span style=\"color: rgb(255, 230, 0);\">" + pass + "</span><br/>\n" +
                        "    </div>\n" +
                        "    <br/>\n" +
                        "    You can check information below:\n" +
                        "    <table border=\"1\" style=\"\">\n" +
                        "        <tr>\n" +
                        "            <th>First Name</th>\n" +
                        "            <th>Last Name</th>\n" +
                        "            <th>Gender</th>\n" +
                        "            <th>Email</th>\n" +
                        "            <th>Date</th>\n" +
                        "            <th>Phone Number</th>\n" +
                        "            <th>Address</th>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td>" + account.getFirstName() + "</td>\n" +
                        "            <td>" + account.getLastName() + "</td>\n" +
                        "            <td>" + gen + "</td>\n" +
                        "            <td>" + account.getEmail() + "</td>\n" +
                        "            <td>" + account.getDob() + "</td>\n" +
                        "            <td>" + account.getPhoneNumber() + "</td>\n" +
                        "            <td>" + account.getAddress() + "</td>\n" +
                        "        </tr>\n" +
                        "         \n" +
                        "    </table>\n" +
                        "</div>\n" +
                        "    \n" +
                        "</body>\n" +
                        "</html>";

                if (sendMail.isStatus()) {
                    sendMail.sendFuncition(account.getEmail(), "Welcome to us", html);
                } else {
                    System.err.println("Pass: " + pass);
                }
                session.removeAttribute("registerSessionForgotHome");
                model.addAttribute("isCheck", true);
                model.addAttribute("accountTam", account);
                model.addAttribute("isRegis", true);
                model.addAttribute("accountTam", new Account());
                model.addAttribute("exitEmail", false);
                model.addAttribute("doneRegis", true);
                return "forgotPassHome";
            } else {
                model.addAttribute("isCheck", true);
                model.addAttribute("accountTam", new Account());
                model.addAttribute("exitEmail", true);
                model.addAttribute("doneRegis", false);
                model.addAttribute("codeFalse", res.getParameter("codeRe"));
                return "forgotPassHome";
            }
        }
        return "redirect:/home/";
    }

    // khi người dùng logout khỏi hệ thống
    @RequestMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model, HttpSession session) {
        if (session.getAttribute("VaiTro") != null) {
            session.removeAttribute("VaiTro");
        }
        if (session.getAttribute("acc") != null) {
            session.removeAttribute("acc");
        }
        if (session.getAttribute("cidSession") != null) {
            session.removeAttribute("cidSession");
        }
        if (session.getAttribute("daLogin") != null) {
            session.removeAttribute("daLogin");
        }
        model.addAttribute("title", "Logout");
        return "redirect:/home/";
    }

    @RequestMapping(value = "/changePassWo")
    public String changePassWo(Model model, HttpSession session, HttpServletRequest res) {
//        http://localhost:2030/home/changePassWo
        if (session.getAttribute("daLogin") != null) {
            if (res.getParameter("newPass") != null && res.getParameter("newPassAgain") != null) {
                Account accSe = (Account) session.getAttribute("acc");
//                System.err.println(accSe.getPassword().equals(BCrypt.hashpw(oldPass.trim(), BCrypt.gensalt(12))));
//                if(accSe.getPassword().equals(BCrypt.hashpw(oldPass.trim(), BCrypt.gensalt(12)))){
//                }
                String newPass = res.getParameter("newPass").trim();
                String newPassAgain = res.getParameter("newPassAgain").trim();
                if (newPass.equals(newPassAgain)) {
                    accSe.setPassword(BCrypt.hashpw(newPass.trim(), BCrypt.gensalt(12)));
                    accountService.save(accSe);
                    model.addAttribute("isSuccessful", true);
                } else {
                    model.addAttribute("newPass", newPass);
                    model.addAttribute("newPassAgain", newPassAgain);
                    model.addAttribute("isError", true);
                }

                return "changePassWo";
            }else {
                return "changePassWo";
            }
        }else {
            return "redirect:/home/";
        }

    }


}
