package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.Scope;
import com.globe.chemicals.operations.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScopeService {

    @Autowired
    private ScopeRepository scopeRepository;

    public Optional<Scope> getScopeById(Long id) {
        return scopeRepository.findById(id);
    }

    public Scope saveScope(Scope scope) {
        return scopeRepository.save(scope);
    }

    public void deleteScope(Long id) {
        scopeRepository.deleteById(id);
    }

    public Optional<Scope> findByTitleName(String titleName) {
        return scopeRepository.findByTitleNameIgnoreCase(titleName);
    }
}
