package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final RaceRepository raceDao;
    public ProfileController(UserRepository userDao, RaceRepository raceDao) {
        this.userDao = userDao;
        this.raceDao = raceDao;
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        Optional<User> user = userDao.findById(id);
        List<Race> bookmarkedRaces = raceDao.findAllBookmarkedByUser(id);
        model.addAttribute("user", user);
        model.addAttribute("bookmarkedRaces", bookmarkedRaces);
        return "users/profile";
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