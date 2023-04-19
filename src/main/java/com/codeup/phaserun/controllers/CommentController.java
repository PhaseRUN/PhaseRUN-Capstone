package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Comment;
import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.CommentRespository;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class CommentController {

    private final RaceRepository raceDao;
    private final CommentRespository commentDao;
    private final UserRepository userDao;

    public CommentController(RaceRepository raceDao, CommentRespository commentDao, UserRepository userDao) {
        this.raceDao = raceDao;
        this.commentDao = commentDao;
        this.userDao = userDao;
    }


    @GetMapping("/comments")
    public String commentPageGet(Model model)
    {
        model.addAttribute("comment", commentDao.findCommentById(1));
        return "users/test";
    }

    @PostMapping("/comments")
    public String commentPagePost(@ModelAttribute Comment comment)
    {
        System.out.println(comment.getBody());

        comment.setUser(userDao.findById(1)); // id is found from the logged in user's session
        comment.setRace(raceDao.findRaceByRaceId(1)); // race id should be found from the race itself (maybe hidden input?)
//
        System.out.println(comment.getUser().getId() + "This is the user id");
        System.out.println(comment.getRace().getRaceId() + " This is the race id");

//        User updateUser = userDao.findById(1);
//        updateUser.setRaces(new ArrayList<>(List.of(raceDao.findRaceByRaceId(1234))));
//        updateUser.setComments(new ArrayList<>(List.of(comment)));

//        Race updateRace = raceDao.findRaceByRaceId(1234);
//        updateRace.setComments(new ArrayList<>(List.of(comment)));
//        updateRace.setUsers(new ArrayList<>(List.of(userDao.findById(1))));

//        System.out.println(comment.getComment());


//        System.out.println(comment);

//        userDao.save(updateUser);
//        raceDao.save(updateRace);
        commentDao.save(comment);

        ////// DONE TESTING //////


        return "redirect:/profile";
    }
}
