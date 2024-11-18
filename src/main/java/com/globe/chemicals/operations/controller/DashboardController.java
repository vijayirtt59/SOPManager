package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private TitleService titleService;

    @GetMapping("/employee/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("titles", titleService.getAllTitles());
        return "employee";
    }
}
