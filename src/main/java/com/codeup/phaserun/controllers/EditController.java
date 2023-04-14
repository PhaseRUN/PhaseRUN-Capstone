package com.codeup.phaserun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditController {

    @GetMapping("/edit")
    public String returnEditPage() {
        return "/main/resources/static/deprecated/edit.html";
    }



}