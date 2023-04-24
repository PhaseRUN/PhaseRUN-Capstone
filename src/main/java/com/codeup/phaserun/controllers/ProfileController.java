package com.codeup.phaserun.controllers;

import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import com.codeup.phaserun.models.*;
import com.codeup.phaserun.repositories.CommentRespository;
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

//    @GetMapping("/profile/{id}/edit")
//    public String returnEditPage(@PathVariable int id, Model model) {
//
//// Temporary list of races for bookmark editing on profile page - Rob (20 April)
//
//        List<RaceInfo> races;
//        try {
//            races = RaceAPI.getRacesFromAPI("500", "78245", "10K");
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        for (RaceInfo race : races) {
//            String descriptionHtml = race.getDescription();
//            String descriptionText = Jsoup.parse(race.getDescription()).text();
//            race.setDescription(Jsoup.parse(race.getDescription()).text());
//        }
//
//        model.addAttribute("races", races);
//
//        model.addAttribute("user", userFromDb);
//        model.addAttribute("comment", new Comment());
//
//        model.addAttribute("comments", dbRaces);
//        return "users/profile";
//    }

    @GetMapping("/profile")
    public String returnProfilePage(Model model, Authentication authentication) {
        User userFromDb = userDao.findByUsername(authentication.getName());
        model.addAttribute("user", userFromDb);
        System.out.println(userFromDb);


//        //TODO: replace user with user session and populated races
//        User user = userDao.findById(1);
//        List<Race> races = user.getRaces();
//        List<Comment> comments = commentDao.findAll();
//        List<Race> dbRaces = raceDao.findAll();
//
//
//        System.out.println(races.get(0).getRaceId());
//
//        List<RaceInfo> racesInfo = new ArrayList<>();
//        for (Race race : user.getRaces()){
//            System.out.println("API for this race id: " + race.getRaceId());
//            RaceInfo raceInfo = new RaceInfo();
//            System.out.println(race.getId());
//            raceInfo.setDbId(race.getId());
//            racesInfo.add(RaceAPI.getRaceInfoFromAPI(race.getRaceId(), raceInfo));
//
//        }
//
//        for (RaceInfo race : racesInfo)
//        {
//            race.setDescription(Jsoup.parse(race.getDescription()).text());
//            System.out.println(race.getRaceId());
//            for(Comment comment : commentDao.findByRaceId(race.getRaceId()))
//            {
//                System.out.println(comment.getBody());
//            }
//        }
//
//
//
////        System.out.println(racesInfo.get(0).getName());
////        System.out.println(racesInfo.get(1).getName());
//        model.addAttribute("races", racesInfo);
//        System.out.println(racesInfo);
//
//        model.addAttribute("comments", comments);
//        System.out.println(comments);
//
//        model.addAttribute("user", user);
//        model.addAttribute("comment", new Comment());
        return "users/profile";

    }


    @PostMapping("/profile")
    public String updateUser(@ModelAttribute User userUpdates, Model model, Authentication authentication) {
        User userToUpdate = userDao.findByUsername(authentication.getName());
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
        User userFromDb = userDao.findById(userToUpdate.getId());
        model.addAttribute("user", userFromDb);

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