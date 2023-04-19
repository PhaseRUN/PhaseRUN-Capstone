package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final RaceRepository raceDao;
    public ProfileController(UserRepository userDao, RaceRepository raceDao) {
        this.userDao = userDao;
        this.raceDao = raceDao;
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
        userToUpdate.setZipcode(userUpdates.getZipcode());
        System.out.println(userToUpdate);
        userDao.save(userToUpdate);
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
        return "users/profile";
    }


}