package org.launchcode.mealtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "My Calorie Tracker");
        return "index";
    }
}