package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserRepository userDao;

    public RegisterController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/register")
    public String returnRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "visitors/register";
    }

    @PostMapping("/register")
    public String createNewUser(@ModelAttribute User user) {
        System.out.println(user.toString());
        userDao.save(user);
        return "redirect:/login";
    }

}