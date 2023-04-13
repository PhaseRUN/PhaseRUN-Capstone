package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaceSearchController {

    @GetMapping("/racesearch")

    public String returnRaceSearchPage(Model model) {
            model.addAttribute("races", new Race());
        return "users/raceSearch";
    }

}