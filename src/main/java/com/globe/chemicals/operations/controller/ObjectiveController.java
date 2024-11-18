package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.entity.Objective;
import com.globe.chemicals.operations.service.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objectives")
public class ObjectiveController {

    @Autowired
    private ObjectiveService objectiveService;

    @GetMapping
    public List<Objective> getAllObjectives() {
        return objectiveService.getAllObjectives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjectiveById(@PathVariable Long id) {
        Optional<Objective> objective = objectiveService.getObjectiveById(id);
        return objective.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Objective createObjective(@RequestBody Objective objective) {
        return objectiveService.saveObjective(objective);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@PathVariable Long id, @RequestBody Objective objectiveDetails) {
        Optional<Objective> objective = objectiveService.getObjectiveById(id);
        if (objective.isPresent()) {
            Objective updatedObjective = objective.get();
            updatedObjective.setTitleName(objectiveDetails.getTitleName());
            updatedObjective.setParagraph(objectiveDetails.getParagraph());
            updatedObjective.setBulletPoints(objectiveDetails.getBulletPoints());
            objectiveService.saveObjective(updatedObjective);
            return ResponseEntity.ok(updatedObjective);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjective(@PathVariable Long id) {
        Optional<Objective> objective = objectiveService.getObjectiveById(id);
        if (objective.isPresent()) {
            objectiveService.deleteObjective(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Objective> searchObjectiveByName(@PathVariable String name) {
        Optional<Objective> objective = objectiveService.findByTitleName(name);
        return objective.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
