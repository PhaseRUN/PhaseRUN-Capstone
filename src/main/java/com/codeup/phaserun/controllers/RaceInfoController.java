package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaceInfoController {

    @GetMapping("/raceinfo")
    public String returnRaceInfoPage() {
        return "users/raceInfo";
    }

}