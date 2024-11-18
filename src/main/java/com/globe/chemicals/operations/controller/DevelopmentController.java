package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.entity.Development;
import com.globe.chemicals.operations.service.DevelopmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developments")
public class DevelopmentController {

    @Autowired
    private DevelopmentService developmentService;

    @GetMapping
    public List<Development> getAllDevelopments() {
        return developmentService.getAllDevelopments();
    }

    @GetMapping("/{id}")
    public Development getDevelopmentById(@PathVariable Long id) {
        return developmentService.getDevelopmentById(id);
    }

    @PostMapping
    public Development createDevelopment(@RequestBody Development development) {
        return developmentService.saveDevelopment(development);
    }

    @PutMapping("/{id}")
    public Development updateDevelopment(@PathVariable Long id, @RequestBody Development development) {
        return developmentService.updateDevelopment(id, development);
    }

    @GetMapping("/search/{activity}")
    public Development searchByActivity(@PathVariable String activity) {
        return developmentService.getDevelopmentByActivity(activity).orElse(null);
    }

    @PostMapping("/mapDevelopment")
    public void mapDevelopmentToTitle(@RequestParam Long developmentId, @RequestParam Long titleId) {
        developmentService.mapDevelopmentToTitle(developmentId, titleId);
    }
}
