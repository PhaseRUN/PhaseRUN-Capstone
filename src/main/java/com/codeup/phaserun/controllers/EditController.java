package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EditController {


    private final UserRepository userDao;

    public EditController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/profile/{id}/edit")
    public String returnEditPage(@PathVariable int id, Model model) {
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
            return "/main/resources/static/deprecated/edit.html";

    }

    @PostMapping("/profile/edit")
    public String updateUser(@ModelAttribute User userUpdates) {
        System.out.println(userUpdates);
        User userToUpdate = userDao.findById(userUpdates.getId());
        userToUpdate.setZipcode(userUpdates.getZipcode());
        System.out.println(userToUpdate);
        userDao.save(userToUpdate);
        return "redirect:/profile";
    }

}