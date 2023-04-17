package com.codeup.phaserun.controllers;


import com.codeup.phaserun.repositories.CommentRespository;
import com.codeup.phaserun.repositories.RaceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaceController {

    private final RaceRepository raceDao;
    private final CommentRespository commentDao;

    public RaceController(RaceRepository raceDao, CommentRespository commentDao) {
        this.raceDao = raceDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/races")
    public String index(Model model) {
        model.addAttribute("races", raceDao.findAll());
        return "races/index";
    }
}
