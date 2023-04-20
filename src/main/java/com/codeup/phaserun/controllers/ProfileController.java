package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.RaceInfo;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final RaceRepository raceDao;
    public ProfileController(UserRepository userDao, RaceRepository raceDao) {
        this.userDao = userDao;
        this.raceDao = raceDao;
    }

    @GetMapping("/profile/{id}/edit")
    public String returnEditPage(@PathVariable int id, Model model) {

// Temporary list of races for bookmark editing on profile page - Rob (20 April)

        List<RaceInfo> races;
        try {
            races = RaceAPI.getRacesFromAPI("500", "78245", "10K");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (RaceInfo race : races) {
            String descriptionHtml = race.getDescription();
            String descriptionText = Jsoup.parse(descriptionHtml).text();
            race.setDescription(descriptionText);
        }
        model.addAttribute("races", races);

// End of temporary list

        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);

        return "users/profile";

    }

    @PostMapping("/profile/{id}/edit")
    public String updateUser(@ModelAttribute User userUpdates, @PathVariable int id, Model model) {
//        System.out.println(userUpdates);
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
//        System.out.println(userToUpdate);
        userDao.save(userToUpdate);
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
        return "users/profile";
    }


}