package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.RaceInfo;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jsoup.Jsoup;

import java.text.ParseException;
import java.util.List;

@Controller
public class RaceSearchController {



    @GetMapping("/race/search")
    public String returnRaceSearchPage() {
        return "users/raceSearch";
    }

    @PostMapping("/race/search")
    public String returnRaceSearchPageWithResults(@RequestParam (name = "race-distance") String distance,
                                                  @RequestParam (name = "search-radius") String searchR,
                                                  @RequestParam (name = "zipcodeRadius") String zipcode, Model model) throws UnirestException, ParseException {
        // ...
        List<RaceInfo> races = RaceAPI.getRacesFromAPI(searchR, zipcode, distance);

        // iterate over the races and extract plain text from the HTML descriptions
        for (RaceInfo race : races) {
            String descriptionHtml = race.getDescription();
            String descriptionText = Jsoup.parse(descriptionHtml).text();
            race.setDescription(descriptionText);
        }

        model.addAttribute("races", races);
        return "users/raceSearch";
    }


}