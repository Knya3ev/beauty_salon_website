package com.knyazev.beauty_salon_website.controllers;

import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.security.PersonDetails;
import com.knyazev.beauty_salon_website.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
public class HomeController {
    PersonDetailsService personDetailsService;

    @Autowired
    public HomeController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/")
    public String seyHello(Model model) {


        model.addAttribute("person",personDetailsService.getCurrentPerson());
        model.addAttribute("title", "hello world");
        return "home/home";
    }
}
