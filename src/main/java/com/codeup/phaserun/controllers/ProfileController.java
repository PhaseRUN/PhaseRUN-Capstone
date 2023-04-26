package com.codeup.phaserun.controllers;

import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.springframework.security.core.Authentication;
import com.codeup.phaserun.models.*;
import com.codeup.phaserun.repositories.CommentRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final RaceRepository raceDao;
    private final CommentRepository commentDao;

    public ProfileController(UserRepository userDao, RaceRepository raceDao, CommentRepository commentDao)
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
        System.out.println("i am here in profile");
        User user = userDao.findByUsername(authentication.getName());
        List<Race> races = raceDao.findAll();;

        System.out.println(user.getEmail());
        List<RaceInfo> racesInfo = new ArrayList<>();
        for (Race race : user.getRaces()){
            System.out.println("API for this race id: " + race.getRaceId());
            RaceInfo raceInfo = new RaceInfo();
            racesInfo.add(RaceAPI.getRaceInfoFromAPI(race.getRaceId(), raceInfo));
        }

        model.addAttribute("races", racesInfo);
        model.addAttribute("user", user);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentDao.findAll());

        return "/users/profile";
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

        userDao.save(userToUpdate);
        User userFromDb = userDao.findById(userToUpdate.getId());
        model.addAttribute("user", userFromDb);

        return "users/profile";
    }

    @PostMapping("/profile/comment")
    public String addAComment(@ModelAttribute Comment comment, Authentication authentication, HttpServletRequest request)
    {
        User user = userDao.findById(((User) authentication.getPrincipal()).getId());
        Race race = (raceDao.findByRaceId(request.getParameter("raceId")));

        comment.setUser(user);
        comment.setRace(race);

        commentDao.save(comment);

//        User user = userDao.findById(1); // id should be obtained from the user session
        List<Comment> userComments = new ArrayList<>(user.getComments());
        userComments.add(commentDao.findById(comment.getId()));
        user.setComments(userComments);

        userDao.save(user);

//        Race race = raceDao.findById(1); // the id for the race in the database should be obtained from the commenting form
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