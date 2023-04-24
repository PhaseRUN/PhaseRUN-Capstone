package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.RaceInfo;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String returnRaceSearchPage(Model model, Authentication authentication) {
        User userFromDb = userDao.findByUsername(authentication.getName());
        model.addAttribute("zipcode", userFromDb.getZipcode());
        return "users/raceSearch";
    }

    @PostMapping("/race/search")
    public String returnRaceSearchPageWithResults(@RequestParam(name = "race-distance") String distance,
                                                  @RequestParam(name = "search-radius") String searchR,
                                                  @RequestParam(name = "zipcodeRadius") String zipcode, Model model,
                                                  Authentication authentication) throws UnirestException, ParseException {
        User user = userDao.findById(((User) authentication.getPrincipal()).getId());
        ArrayList<String> userRaceIds = new ArrayList<>();
        for (Race race : user.getRaces()) {
            userRaceIds.add(String.valueOf(race.getRaceId()));
        }

        // ...
        System.out.println(distance);
        System.out.println(searchR);
        System.out.println(zipcode);

        List<RaceInfo> races = RaceAPI.getRacesFromAPI(searchR, zipcode, distance);
        for ( RaceInfo race : races) {
            String descriptionHtml = race.getDescription();
            String descriptionText = Jsoup.parse(descriptionHtml).text();
            race.setDescription(descriptionText);
//            System.out.println();
//            System.out.println(race.getYellowStartDate() + "this is the yellow date");
//            System.out.println(race.getRaceDate() + "this is the race date");
//            System.out.println(race.getGreenStartDate() + "This is the green date");
            //TODO: store in object or return what the date color should be
            System.out.println(RaceInfo.redYellowGreen(race.getRaceDate(), race.getYellowStartDate(), race.getGreenStartDate())); // RETURNS A STRING OF RED, YELLOW, OR GREEN
        }

        System.out.println(Arrays.toString(userRaceIds.toArray()));
        model.addAttribute("userRaceIds", userRaceIds.toArray());
        model.addAttribute("races", races);
        return "users/raceSearch";
    }


    @PostMapping("/races/bookmark")
    public String bookmarkRace(@RequestParam("raceId") int raceId, Authentication authentication, HttpServletResponse response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User userId = (User) authentication.getPrincipal();
        System.out.println(userId.getId());
        User user = userDao.findById(userId.getId());
        Race race = new Race(raceId, new ArrayList<>(List.of(user)));

        raceDao.save(race);
        System.out.println(mapper.writeValueAsString(race));

        List<Race> races = new ArrayList<>(user.getRaces());
        System.out.println(mapper.writeValueAsString(races));
        races.add(race);

        user.setRaces(races);
        System.out.println(mapper.writeValueAsString(user.getRaces()));
        userDao.save(user);
        return "redirect:/profile";
    }

}