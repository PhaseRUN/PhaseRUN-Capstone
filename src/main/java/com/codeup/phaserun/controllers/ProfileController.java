package com.codeup.phaserun.controllers;


import com.codeup.phaserun.models.*;

import com.codeup.phaserun.repositories.CommentRespository;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import org.jsoup.Jsoup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


import java.text.ParseException;
import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final RaceRepository raceDao;
    private final CommentRespository commentDao;

    public ProfileController(UserRepository userDao, RaceRepository raceDao, CommentRespository commentDao)
    {
        this.userDao = userDao;
        this.raceDao = raceDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/profile")
    public String returnProfilePage(Model model) {
        User userFromDb = userDao.findById(1);
        model.addAttribute("user", userFromDb);
        model.addAttribute("comment", new Comment());
        return "users/profile";
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
        model.addAttribute("comment", new Comment());
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
@GetMapping("/profile/")
    public String returnProfilePage(@PathVariable int id, Model model) {
        User userFromDb = userDao.findById(id);
        model.addAttribute("user", userFromDb);
        model.addAttribute("comment", new Comment());
        return "users/profile";
    }

    @PostMapping("/profile/comment")
    public String addAComment(@ModelAttribute Comment comment)
    {
        comment.setUser(userDao.findById(1));
        comment.setRace(raceDao.findById(1));

        commentDao.save(comment);

        User user = userDao.findById(1); // id should be obtained from the user session
        List<Comment> userComments = new ArrayList<>(user.getComments());
        userComments.add(commentDao.findById(comment.getId()));
        user.setComments(userComments);

        userDao.save(user);

        Race race = raceDao.findById(1); // the id for the race in the database should be obtained from the commenting form
        List<Comment> raceComments = new ArrayList<>(race.getComments());
        raceComments.add(commentDao.findById(comment.getId()));
        race.setComments(raceComments);

        raceDao.save(race);

        System.out.println(commentDao.findAll());
        System.out.println();
        System.out.println(user);
        System.out.println();
        System.out.println(race);

        return "redirect:/profile";
    }

}