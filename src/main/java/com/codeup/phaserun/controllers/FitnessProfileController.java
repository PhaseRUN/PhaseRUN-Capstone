package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FitnessProfileController {

    @GetMapping("/fitnessProfile")
    public String returnFitnessProfilePage() {
        return "/main/resources/static/deprecated/fitnessProfile.html";
    }



}