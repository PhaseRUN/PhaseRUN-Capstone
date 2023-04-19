package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final UserRepository userDao;

    public ProfileController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/profile")
    public String returnProfilePage(Model model) {
        User userFromDb = userDao.findById(1);
        model.addAttribute("user", userFromDb);
        return "users/profile";

    }

    @GetMapping("/profile/{id}/edit")
    public String returnEditPage(@PathVariable int id, Model model) {
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
        return "users/profile";

    }

    @PostMapping("/profile/{id}/edit")
    public String updateUser(@ModelAttribute User userUpdates, @PathVariable int id, Model model) {
        System.out.println(userUpdates);
        User userToUpdate = userDao.findById(userUpdates.getId());
        if (userUpdates.getZipcode() != 0) {
            userToUpdate.setZipcode(userUpdates.getZipcode());
        }
        if (userUpdates.getRunningExp() != null) {
            userToUpdate.setRunningExp(userUpdates.getRunningExp());
        }
        if (userUpdates.getActivityLvl() != null) {
            userToUpdate.setActivityLvl(userUpdates.getActivityLvl());
        }
        System.out.println(userToUpdate);
        userDao.save(userToUpdate);
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
        return "users/profile";
    }
@GetMapping("/profile/")
    public String returnProfilePage(@PathVariable int id, Model model) {
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
        return "users/profile";
    }

}