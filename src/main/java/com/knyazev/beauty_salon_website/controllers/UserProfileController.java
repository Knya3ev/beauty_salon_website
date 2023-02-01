package com.knyazev.beauty_salon_website.controllers;


import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.security.PersonDetails;
import com.knyazev.beauty_salon_website.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    PersonDetailsService personDetailsService;


    @Autowired
    public UserProfileController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/")
    public String home(Model model){
        PersonDetails person = personDetailsService.getCurrentPerson();
        model.addAttribute("person",person);

        return "profile/home";
    }
}
