package com.globe.chemicals.operations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/employee")
    public String employee() {
        return "employee";
    }

    @GetMapping("/titleManagement")
    public String titleManagement() {
        return "titleManagement";
    }

    @GetMapping("/objectiveManagement")
    public String objectiveManagement() {
        return "objectiveManagement";
    }

    @GetMapping("/scopeManagement")
    public String scopeManagement() {
        return "scopeManagement";
    }

    @GetMapping("/responsibilityManagement")
    public String responsibilityManagement() {
        return "responsibilityManagement";
    }

    @GetMapping("/definitionManagement")
    public String definitionManagement() {
        return "definitionManagement";
    }

    @GetMapping("/developmentManagement")
    public String developmentManagement() {
        return "developmentManagement";
    }

    @GetMapping("/employeeDetails")
    public String employeeDetails(@RequestParam("id") String titleId, Model model) {
        return "employeeDetails";
    }
}
