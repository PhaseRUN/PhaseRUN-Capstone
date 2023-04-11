package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RaceSearchController {

    @GetMapping("/racesearch")

    public String returnRaceSearchPage() {
        return "users/raceSearch";
    }

}