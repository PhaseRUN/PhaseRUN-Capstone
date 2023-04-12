package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RaceSearchController {

    @GetMapping("/racesearch")

    public String returnRaceSearchPage() {
        return "users/raceSearch";
    }

    @PostMapping("/racesearch")
    public String returnRaceSearchPageWithResults(@RequestParam (name = "race-distance-min") String minD,
                                                  @RequestParam (name = "race-distance-max") String maxD,
                                                  @RequestParam (name = "search-radius") String searchR,
                                                  @RequestParam (name = "zipcodeRadius") String zipcode) throws UnirestException {


        List<Race> races = RaceAPI.getRacesFromAPI(searchR, zipcode, minD, maxD);

    }

}