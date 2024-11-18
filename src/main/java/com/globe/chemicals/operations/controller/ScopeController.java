package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.entity.Objective;
import com.globe.chemicals.operations.entity.Scope;
import com.globe.chemicals.operations.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/scopes")
public class ScopeController {

    @Autowired
    private ScopeService scopeService;

    @PostMapping
    public Scope createScope(@RequestBody Scope scope) {
        return scopeService.saveScope(scope);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scope> getScopeById(@PathVariable Long id) {
        Optional<Scope> scope = scopeService.getScopeById(id);
        return scope.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scope> updateScope(@PathVariable Long id, @RequestBody Scope scopeDetails) {
        Optional<Scope> scope = scopeService.getScopeById(id);
        if (scope.isPresent()) {
            Scope updatedScope = scope.get();
            updatedScope.setTitleName(scopeDetails.getTitleName());
            updatedScope.setParagraph(scopeDetails.getParagraph());
            scopeService.saveScope(updatedScope);
            return ResponseEntity.ok(updatedScope);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScope(@PathVariable Long id) {
        Optional<Scope> scope = scopeService.getScopeById(id);
        if (scope.isPresent()) {
            scopeService.deleteScope(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Scope> searchScopeByName(@PathVariable String name) {
        Optional<Scope> scope = scopeService.findByTitleName(name);
        return scope.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
