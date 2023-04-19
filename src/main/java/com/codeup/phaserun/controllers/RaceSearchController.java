package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.RaceInfo;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.jsoup.Jsoup;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
public class RaceSearchController {

    private final RaceRepository raceRepository;
    private final UserRepository userRepository;

    public RaceSearchController(RaceRepository raceRepository, UserRepository userRepository) {
        this.raceRepository = raceRepository;
        this.userRepository = userRepository;
    }

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

//    @PostMapping("/bookmark")
//    public String bookmarkRace(@RequestParam("raceId") Long raceId, @RequestParam("userId") Long userId) {
//
//        Optional<Race> raceOptional = raceRepository.findById(raceId);
//        User user = userRepository.findById(1);
//
//        if (raceOptional.isPresent()) {
//            Race race = raceOptional.get();
//            user.getBookmarkedRaces().add(race);
//            userRepository.save(user);
//        }
//
//        return "users/raceSearch";
//    }


}