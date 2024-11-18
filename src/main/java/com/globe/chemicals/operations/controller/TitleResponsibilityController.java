package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.entity.TitleResponsibility;
import com.globe.chemicals.operations.service.TitleResponsibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mappings")
public class TitleResponsibilityController {

    @Autowired
    private TitleResponsibilityService mappingService;

    // Add a new mapping
    @PostMapping
    public ResponseEntity<TitleResponsibility> addMapping(@RequestBody TitleResponsibility mapping) {
        TitleResponsibility savedMapping = mappingService.saveMapping(mapping);
        return ResponseEntity.ok(savedMapping);
    }

    // Get all mappings for a specific title
    @GetMapping("/{titleId}")
    public ResponseEntity<List<TitleResponsibility>> getMappings(@PathVariable Long titleId) {
        List<TitleResponsibility> mappings = mappingService.getMappingsByTitleId(titleId);
        return ResponseEntity.ok(mappings);
    }

    // Delete a mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMapping(@PathVariable Long id) {
        mappingService.deleteMapping(id);
        return ResponseEntity.noContent().build();
    }
}
