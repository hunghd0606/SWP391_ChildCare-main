package com.example.SWP_1631.controller;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Kindergartner;
import com.example.SWP_1631.entity.Role;
import com.example.SWP_1631.service.AttendanceService;
import com.example.SWP_1631.service.KindergartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/kinderController")
public class KinderController {

    @Autowired
    private KindergartnerService ks;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/")
    public String view(Model model) {
        List<Kindergartner> listKinder = ks.getListKinder();
        model.addAttribute("listK", listKinder);
        System.out.println(listKinder);
        return "admin/kinderAdminPage";
    }

    @RequestMapping(value = "/saveKinder", method = RequestMethod.POST)
    public String save(Kindergartner kinder) {
        ks.save(kinder);
        return "redirect:/admin/";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editKinder(@RequestParam("id") Integer KinderId, Model model) {
        Optional<Kindergartner> kindergartnerEdit = ks.getKindergartnerById(KinderId);
        kindergartnerEdit.ifPresent(kindergartner -> model.addAttribute("Kindergartner", kindergartner));
        return "admin/kinderAdminUpdate";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteKinder(@RequestParam("id") Integer KinderId, Model model) {
        attendanceService.deleteAttendanceByIdKinder(KinderId);
        ks.delete(KinderId);
        return "redirect:/kinderController/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("id") Integer KinderId, HttpServletRequest res) throws Exception {
        Kindergartner kd = new Kindergartner();
        Optional<Kindergartner> kindergartnerDB = ks.getKindergartnerById(KinderId);
        if (kindergartnerDB.isPresent()) {
            kd = kindergartnerDB.get();
        }
        kd.setKinderId(KinderId);
        kd.setFirstName(res.getParameter("txtFirstName"));
        kd.setLastName(res.getParameter("txtLastName"));
        kd.setDob(res.getParameter("dob"));
        boolean gen = !res.getParameter("gender").equals("female");
        kd.setGender(gen);
        kd.setImg(res.getParameter("txtImg"));
        ks.update(kd);
        return "redirect:/kinderController/";
    }


}
