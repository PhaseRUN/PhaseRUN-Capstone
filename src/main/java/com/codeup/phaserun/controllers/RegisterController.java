package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class RegisterController {

    private final UserRepository userDao;

    public RegisterController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/register")
    public String returnRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "/main/resources/static/deprecated/register.html";
    }

    @PostMapping("/register")
    public String createNewUser(@ModelAttribute User user, @RequestParam(name = "date-of-birth") String birth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date langDate = null;
        try {
            langDate = sdf.parse(birth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        java.sql.Date sqlDate = new java.sql.Date(langDate.getTime());
        user.setBirthDate(sqlDate);
        userDao.save(user);
        return "redirect:/login";
    }

}