package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.RaceInfo;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.jsoup.Jsoup;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RaceSearchController {

    private final RaceRepository raceDao;
    private final UserRepository userDao;

    public RaceSearchController(RaceRepository raceDao, UserRepository userDao) {
        this.raceDao = raceDao;
        this.userDao = userDao;
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

    @PostMapping("/races/bookmark")
    public String bookmarkRace(@RequestParam("raceId") int raceId, HttpServletResponse response) {
        User user = userDao.findById(1);
        Race race = new Race(Integer.toString(raceId), new ArrayList<>(List.of(user)));

        raceDao.save(race);
        System.out.println(race);

        List<Race> races = new ArrayList<>(user.getRaces());
        races.add(raceDao.findById(1));
        user.setRaces(races);

        userDao.save(user);

        return "redirect:/profile";
    }

}