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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class RaceController {

    private final RaceRepository raceDao;
    private final CommentRespository commentDao;
    private final UserRepository userDao;

    public RaceController(RaceRepository raceDao, CommentRespository commentDao, UserRepository userDao) {
        this.raceDao = raceDao;
        this.commentDao = commentDao;
        this.userDao = userDao;
    }

    @GetMapping("/races")
    public String index(Model model)
    {
        model.addAttribute("races", raceDao.findAll());


        //// THIS IS JUST FOR TESTING PURPOSES /////
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date testDate = null;
        try
        {
            testDate = formatter.parse("03/30/1990");
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }

        User testUser = new User("test", "test@email.com", "test123", User.RunningExpEnum.RECREATIONAL, User.ActivityLvlEnum.BEGINNER, 78258, testDate, new ArrayList<>());
        Race testRace = new Race(1234, new ArrayList<>(List.of(testUser)), new ArrayList<>());
        Comment testComment = new Comment("This is a test comment :)", userDao.findById(1), raceDao.findRaceByRaceId(1234));

        User updateUser = userDao.findById(1);
        updateUser.setRaces(new ArrayList<>(List.of(testRace)));

        Race updateRace = raceDao.findRaceByRaceId(1234);
        updateRace.setComments(new ArrayList<>(List.of(testComment)));


        commentDao.save(testComment);
        userDao.save(updateUser);
        raceDao.save(updateRace);




        ////// DONE TESTING //////



        return "races/index";
    }
}
