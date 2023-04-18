package com.codeup.phaserun.controllers;

import com.codeup.phaserun.models.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommentController {
    @GetMapping("/comments")
    public String commentPageGet(Model model)  {
        model.addAttribute("comment", new Comment());
        return "users/test";
    }

    @PostMapping("/comments")
    public String commentPagePost()  {
        return "users/test";
    }
}
