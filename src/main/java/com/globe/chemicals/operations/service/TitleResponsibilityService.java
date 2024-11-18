package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.TitleResponsibility;
import com.globe.chemicals.operations.repository.TitleResponsibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleResponsibilityService {

    @Autowired
    private TitleResponsibilityRepository repository;

    // Save mapping
    public TitleResponsibility saveMapping(TitleResponsibility mapping) {
        return repository.save(mapping);
    }

    // Get all mappings for a specific title
    public List<TitleResponsibility> getMappingsByTitleId(Long titleId) {
        return repository.findByTitle_Id(titleId);
    }

    // Delete mapping
    public void deleteMapping(Long id) {
        repository.deleteById(id);
    }
}
