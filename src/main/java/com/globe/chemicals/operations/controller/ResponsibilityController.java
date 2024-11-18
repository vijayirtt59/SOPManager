package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.entity.Objective;
import com.globe.chemicals.operations.entity.Responsibility;
import com.globe.chemicals.operations.service.ResponsibilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/responsibilities")
@Slf4j
public class ResponsibilityController {

    @Autowired
    private ResponsibilityService responsibilityService;

    // Get all responsibilities
    @GetMapping
    public List<Responsibility> getAllResponsibilities() {
        return responsibilityService.getAllResponsibilities();
    }

    // Get responsibility by ID
    @GetMapping("/{id}")
    public ResponseEntity<Responsibility> getResponsibilityById(@PathVariable Long id) {
        return responsibilityService.getResponsibilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new responsibility
    @PostMapping
    public Responsibility createResponsibility(@RequestBody Responsibility responsibility) {
        return responsibilityService.createResponsibility(responsibility);
    }

    // Update an existing responsibility
    @PutMapping(value = "/{id}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<Responsibility> updateResponsibility(
            @PathVariable Long id,
            @RequestBody Responsibility updatedResponsibility) {
        Optional<Responsibility> responsibility = responsibilityService.getResponsibilityById(id);
        if (responsibility.isPresent()) {
            Responsibility updatedRes = responsibility.get();
            updatedRes.setPosition(updatedResponsibility.getPosition());
            updatedRes.setSubPositions(updatedResponsibility.getSubPositions());
            updatedRes.setNote(updatedResponsibility.getNote());
            updatedRes.setBulletPoints(updatedResponsibility.getBulletPoints());
            responsibilityService.createResponsibility(updatedRes);
            return ResponseEntity.ok(updatedRes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a responsibility
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponsibility(@PathVariable Long id) {
        if (responsibilityService.deleteResponsibility(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/mapResponsibility")
    public ResponseEntity<String> mapResponsibilityToTitle(
            @RequestParam Long responsibilityId,
            @RequestParam Long titleId) {
        if (responsibilityService.mapResponsibilityToTitle(responsibilityId, titleId)) {
            return ResponseEntity.ok("Responsibility mapped to Title successfully!");
        } else {
            return ResponseEntity.badRequest().body("Mapping failed!");
        }
    }

    @GetMapping("/search/{position}")
    public ResponseEntity<Responsibility> getResponsibilityById(@PathVariable String position) {
        Optional<Responsibility> responsibility = responsibilityService.getResponsibilityByPosition(position);
        return responsibility.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
