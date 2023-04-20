package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Comment;
import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.CommentRespository;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
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
        userComments.add(commentDao.findById(1));
        user.setComments(userComments);

        Race race = raceDao.findById(1); // the id for the race in the database

        System.out.println(commentDao.findById(1));
        System.out.println();
        System.out.println(userDao.findById(1).getComments());

        return "redirect:/profile";
    }

}