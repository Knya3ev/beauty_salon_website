package com.knyazev.beauty_salon_website.controllers;


import com.knyazev.beauty_salon_website.form.CheckboxesForm;
import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.services.CraftsmenService;
import com.knyazev.beauty_salon_website.services.DaysOfTheCraftsmenService;
import com.knyazev.beauty_salon_website.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/scheduling")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SchedulingController {
    private final SchedulingService schedulingService;
    private final CraftsmenService craftsmenService;
    private final DaysOfTheCraftsmenService daysOfTheCraftsmenService;

    @Autowired
    public SchedulingController(SchedulingService schedulingService, CraftsmenService craftsmenService, DaysOfTheCraftsmenService daysOfTheCraftsmenService) {
        this.schedulingService = schedulingService;
        this.craftsmenService = craftsmenService;
        this.daysOfTheCraftsmenService = daysOfTheCraftsmenService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Craftsmen> allMaster = craftsmenService.getAllMaster();
        model.addAttribute("master", allMaster);
        return "admin/scheduling/home";
    }


    @GetMapping("/add_date_for_craftsmen/{id}/{month}")
    public String addWorksDaysForMaster(@PathVariable(value = "id") long id,
                                        @PathVariable(value = "month") String  month, Model model) {
        Optional<Craftsmen> master = craftsmenService.getMasterId(id);
        if (master.isEmpty()) return "redirect:/scheduling/";

        schedulingService.checkingActualityData(master.get().getWorkdays()); //проверка актуальных дат

        model.addAttribute("master", master.get());
        model.addAttribute("nameMonth", schedulingService.getNameMonth(month));
        model.addAttribute("masterData", master.get().getWorkdays());
        model.addAttribute("list", schedulingService.getDaysOfTheCurrentMonth(master.get().getWorkdays(),month));
        CheckboxesForm checkboxesForm = new CheckboxesForm();
        model.addAttribute("ch", checkboxesForm);
        return "admin/scheduling/add_works_days";
    }

    @PostMapping("/checkbox/{id}")
    public String checkbox(@PathVariable(value = "id") long id, @ModelAttribute("ch") CheckboxesForm checkboxesForm) throws ParseException {
        Optional<Craftsmen> master = craftsmenService.getMasterId(id);
        daysOfTheCraftsmenService.saveDataForCraftsmen(checkboxesForm.getCheckedItems(), id);
        return "redirect:/scheduling/add_date_for_craftsmen/" + id + "/current";
    }

    @GetMapping("/remove_data_for_craftsmen/{idData}/{idCraftsmen}")
    public String removeData(@PathVariable(value = "idData")long idData, @PathVariable(value = "idCraftsmen") long idCraftsmen){
        daysOfTheCraftsmenService.removeData(idData,idCraftsmen);
        return "redirect:/scheduling/add_date_for_craftsmen/" + idCraftsmen + "/current" ;
    }

}
