package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.entity.Definition;
import com.globe.chemicals.operations.service.DefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/definitions")
public class DefinitionController {
    @Autowired
    private DefinitionService definitionService;

    // Get all definitions
    @GetMapping
    public List<Definition> getAllDefinitions() {
        return definitionService.getAllDefinitions();
    }

    // Get a definition by name
    @GetMapping("/search/{name}")
    public Definition getDefinitionByName(@PathVariable String name) {
        return definitionService.getDefinitionByName(name);
    }

    // Create a new definition
    @PostMapping
    public Definition createDefinition(@RequestBody Definition definition) {
        return definitionService.createDefinition(definition);
    }

    // Update an existing definition
    @PutMapping("/{id}")
    public Definition updateDefinition(@PathVariable Long id, @RequestBody Definition updatedDefinition) {
        return definitionService.updateDefinition(id, updatedDefinition);
    }

    // Map definition to title
    @PostMapping("/mapDefinition")
    public void mapDefinitionToTitle(@RequestParam Long definitionId, @RequestParam Long titleId) {
        definitionService.mapDefinitionToTitle(definitionId, titleId);
    }
}
