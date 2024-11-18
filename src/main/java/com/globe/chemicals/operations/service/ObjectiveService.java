package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.Objective;
import com.globe.chemicals.operations.repository.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectiveService {

    @Autowired
    private ObjectiveRepository objectiveRepository;

    public List<Objective> getAllObjectives() {
        return objectiveRepository.findAll();
    }

    public Optional<Objective> getObjectiveById(Long id) {
        return objectiveRepository.findById(id);
    }

    public Objective saveObjective(Objective objective) {
        return objectiveRepository.save(objective);
    }

    public void deleteObjective(Long id) {
        objectiveRepository.deleteById(id);
    }

    public Optional<Objective> findByTitleName(String titleName) {
        return objectiveRepository.findByTitleNameIgnoreCase(titleName);
    }
}
