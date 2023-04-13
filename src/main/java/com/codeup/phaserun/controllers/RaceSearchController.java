package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaceSearchController {

    @GetMapping("/raceSearch")

    public String returnRaceSearchPage() {
        return "users/raceSearch";
    }

}