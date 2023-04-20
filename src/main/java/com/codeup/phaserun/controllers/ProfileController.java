package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.RaceAPI;
import com.codeup.phaserun.models.RaceInfo;
import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.RaceRepository;
import com.codeup.phaserun.repositories.UserRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final RaceRepository raceDao;

    public ProfileController(UserRepository userDao, RaceRepository raceDao) {
        this.userDao = userDao;
        this.raceDao = raceDao;
    }

    @GetMapping("/profile")
    public String returnControllerPage(Model model) {
        //TODO: replace user with user session and populated races
        User user = userDao.findById(1);
        List<Race> races = raceDao.findAll();;

        System.out.println(races.get(0).getRaceId());

        List<RaceInfo> racesInfo = new ArrayList<RaceInfo>();
        for (Race race : user.getRaces()){
            System.out.println("API for this race id: " + race.getRaceId());
            RaceInfo raceInfo = new RaceInfo();
            racesInfo.add(RaceAPI.getRaceInfoFromAPI(race.getRaceId(), raceInfo));
        }
        System.out.println(racesInfo.get(0).getName());
        System.out.println(racesInfo.get(1).getName());
        model.addAttribute("userRaces", racesInfo);
        return "/users/profile";
    }


}