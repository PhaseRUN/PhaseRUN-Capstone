package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserLandingController{

    @GetMapping("/userlanding")
    public String returnUserLandingPage() {
        return "users/userLanding";
    }



}