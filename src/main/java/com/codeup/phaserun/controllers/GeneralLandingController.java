package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralLandingController
{
    @GetMapping("/")
    public String returnHomePage() {
        return "visitors/home";
    }

}
