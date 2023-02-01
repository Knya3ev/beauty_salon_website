package com.knyazev.beauty_salon_website.controllers;


import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.DaysOfTheCraftsmen;
import com.knyazev.beauty_salon_website.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/make_an_appointment")
public class MakeAnAppointmentController {
    private final PersonDetailsService personDetailsService;
    private final CraftsmenService craftsmenService;
    private final SchedulingService schedulingService;
    private final DaysOfTheCraftsmenService daysOfTheCraftsmenService;
    private final MakeAnAppointmentService makeAnAppointmentService;


    @Autowired
    public MakeAnAppointmentController(PersonDetailsService personDetailsService, CraftsmenService craftsmenService,
                                       SchedulingService schedulingService, DaysOfTheCraftsmenService daysOfTheCraftsmenService,
                                       MakeAnAppointmentService makeAnAppointmentService) {
        this.personDetailsService = personDetailsService;
        this.craftsmenService = craftsmenService;
        this.schedulingService = schedulingService;
        this.daysOfTheCraftsmenService = daysOfTheCraftsmenService;
        this.makeAnAppointmentService = makeAnAppointmentService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Craftsmen> craftsmenList = craftsmenService.getAllMaster();

        model.addAttribute("craftsmenList", craftsmenList);

        return "make_an_appointment/home";
    }
    @GetMapping("/record/{idMaster}")
    public String recordForCraftsmen(@PathVariable(name = "idMaster") long idMaster, Model model){
        Craftsmen craftsmen = craftsmenService.getMasterId(idMaster).get();
        List<DaysOfTheCraftsmen> list = daysOfTheCraftsmenService.sortData(craftsmen.getWorkdays());
        model.addAttribute("master",craftsmen);
        model.addAttribute("list",list);
        return "make_an_appointment/record";
    }

    @GetMapping("/get_time/{id}/{data}")
    public String getTimeForData(@PathVariable(name = "id") long idMaster,
                                 @PathVariable(name = "data") String data,
                                 Model model){
        Craftsmen craftsmen = craftsmenService.getMasterId(idMaster).get();
        List<String> list =makeAnAppointmentService.getActualityTimeForCraftsmen(idMaster,data);
        model.addAttribute("master",craftsmen);
        model.addAttribute("list",list);
        model.addAttribute("data",data);
        return "make_an_appointment/get_time";
    }

    @PostMapping("/add_data/{id}/{data}/{time}")
    public String addAppointment(@PathVariable(name = "id") long idMaster,
                                 @PathVariable(name = "data") String data,
                                 @PathVariable(name = "time") String time){


        return "redirect:make_an_appointment/";
    }
}
