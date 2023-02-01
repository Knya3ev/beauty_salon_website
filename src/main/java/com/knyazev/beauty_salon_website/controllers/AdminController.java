package com.knyazev.beauty_salon_website.controllers;

import com.knyazev.beauty_salon_website.form.CreateMasterForm;
import com.knyazev.beauty_salon_website.services.AdminService;
import com.knyazev.beauty_salon_website.services.CraftsmenService;
import com.knyazev.beauty_salon_website.util.CreateMasterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final CraftsmenService craftsmenService;
    private final CreateMasterFormValidator createMasterFormValidator;


    @Autowired
    public AdminController(AdminService adminService, CraftsmenService craftsmenService,
                           CreateMasterFormValidator createMasterFormValidator) {

        this.adminService = adminService;
        this.craftsmenService = craftsmenService;
        this.createMasterFormValidator = createMasterFormValidator;
    }


    @GetMapping("/")
    public String helloAdmin() {
        return "admin/home";
    }


    @GetMapping("/getAllPeople")
    public String getAllPeople(Model model) {
        model.addAttribute("people", adminService.getAllPeople());
        return "admin/all_people";
    }

    @GetMapping("/getAllCraftsmen")
    public String getAllMaster(Model model) {
        model.addAttribute("master", adminService.getAllMaster());
        return "admin/all_craftsmen";
    }

    @GetMapping("/createMaster")
    public String createMaster(@ModelAttribute("formMaster") CreateMasterForm createMasterForm) {
        return "admin/createMaster";
    }

    @PostMapping("/createMaster")
    public String addMaster(@ModelAttribute("formMaster") @Valid CreateMasterForm createMasterForm,
                            BindingResult bindingResult) {
        createMasterFormValidator.validate(createMasterForm, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/createMaster";

        craftsmenService.addMaster(createMasterForm.getPerson(), createMasterForm.getCraftsmen());
        return "redirect:/admin/getAllCraftsmen";
    }


}
