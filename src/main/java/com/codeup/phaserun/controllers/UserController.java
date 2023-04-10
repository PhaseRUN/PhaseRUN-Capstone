package com.codeup.phaserun.controllers;

import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", userDao.findAll());
        return "users/index";
    }
}
