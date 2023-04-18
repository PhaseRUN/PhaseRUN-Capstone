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
        model.addAttribute("comment", commentDao.findById(1));
        return "users/test";
    }

    @PostMapping("/comments")
    public String commentPagePost(@ModelAttribute Comment comment)
    {
        //// THIS IS JUST FOR TESTING PURPOSES /////
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//        Comment addedComment = new Comment();
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
        Race testRace = new Race(1234, new ArrayList<>(), new ArrayList<>());

//        comment.setComment(comment.getComment());
        comment.setUser(userDao.findById(1)); // id is found from the logged in user's session
        userDao.findById(1).getRaces();
        /// for loop getRaces
        ///if exist, that means bookmark exist

        raceDao.findRaceByRaceId(1).getUsers().get(0).getId();
        comment.setRace(raceDao.findRaceByRaceId(1234)); // race id should be found from the race itself (maybe hidden input?)

        User updateUser = userDao.findById(1);
        updateUser.setRaces(new ArrayList<>(List.of(raceDao.findRaceByRaceId(1234))));
//        updateUser.setComments(new ArrayList<>(List.of(comment)));

        Race updateRace = raceDao.findRaceByRaceId(1234);
//        updateRace.setComments(new ArrayList<>(List.of(comment)));
        updateRace.setUsers(new ArrayList<>(List.of(userDao.findById(1))));

//        System.out.println(comment.getComment());


//        System.out.println(comment);

        userDao.save(updateUser);
        raceDao.save(updateRace);
        commentDao.save(comment);

        ////// DONE TESTING //////


        return "redirect:/profile";
    }
}
