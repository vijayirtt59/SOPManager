package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.Development;
import com.globe.chemicals.operations.entity.Title;
import com.globe.chemicals.operations.repository.DevelopmentRepository;
import com.globe.chemicals.operations.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DevelopmentService {
    @Autowired
    private DevelopmentRepository developmentRepository;

    @Autowired
    private TitleRepository titleRepository;

    // Get all developments
    public List<Development> getAllDevelopments() {
        return developmentRepository.findAll();
    }

    // Save a new development
    public Development saveDevelopment(Development development) {
        return developmentRepository.save(development);
    }

    // Find by activity
    public Optional<Development> getDevelopmentByActivity(String activity) {
        return developmentRepository.findByActivity(activity);
    }

    // Find by ID
    public Development getDevelopmentById(Long id) {
        return developmentRepository.findById(id).orElse(null);
    }

    // Update development
    public Development updateDevelopment(Long id, Development developmentDetails) {
        Development development = getDevelopmentById(id);
        if (development != null) {
            development.setNumber(developmentDetails.getNumber());
            development.setActivity(developmentDetails.getActivity());
            development.setResponsible(developmentDetails.getResponsible());
            development.setDescription(developmentDetails.getDescription());
            return developmentRepository.save(development);
        }
        return null;
    }

    // Map developmentId to title
    public void mapDevelopmentToTitle(Long developmentId, Long titleId) {
        Optional<Development> development = developmentRepository.findById(developmentId);
        Optional<Title> title = titleRepository.findById(titleId);

        if (development.isPresent() && title.isPresent()) {
            development.get().getTitles().add(title.get());
            developmentRepository.save(development.get());
        }
    }
}
