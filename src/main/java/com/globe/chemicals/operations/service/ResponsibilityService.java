package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.Responsibility;
import com.globe.chemicals.operations.entity.Title;
import com.globe.chemicals.operations.repository.ResponsibilityRepository;
import com.globe.chemicals.operations.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsibilityService {

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @Autowired
    private TitleRepository titleRepository;

    public List<Responsibility> getAllResponsibilities() {
        return responsibilityRepository.findAll();
    }

    public Optional<Responsibility> getResponsibilityById(Long id) {
        return responsibilityRepository.findById(id);
    }

    public Responsibility createResponsibility(Responsibility responsibility) {
        return responsibilityRepository.save(responsibility);
    }

    public Optional<Responsibility> updateResponsibility(Long id, Responsibility updatedResponsibility) {
        return responsibilityRepository.findById(id).map(responsibility -> {
            responsibility.setPosition(updatedResponsibility.getPosition());
            responsibility.setSubPositions(updatedResponsibility.getSubPositions());
            responsibility.setNote(updatedResponsibility.getNote());
            responsibility.setBulletPoints(updatedResponsibility.getBulletPoints());
            responsibility.setTitles(updatedResponsibility.getTitles());
            return responsibilityRepository.save(responsibility);
        });
    }

    public boolean deleteResponsibility(Long id) {
        if (responsibilityRepository.existsById(id)) {
            responsibilityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean mapResponsibilityToTitle(Long responsibilityId, Long titleId) {
        Optional<Responsibility> responsibilityOptional = responsibilityRepository.findById(responsibilityId);
        Optional<Title> titleOptional = titleRepository.findById(titleId);

        if (responsibilityOptional.isPresent() && titleOptional.isPresent()) {
            Responsibility responsibility = responsibilityOptional.get();
            Title title = titleOptional.get();
            responsibility.getTitles().add(title);
            responsibilityRepository.save(responsibility);
            return true;
        }
        return false;
    }

    public Optional<Responsibility> getResponsibilityByPosition(String position){
        return responsibilityRepository.findByPositionContainingIgnoreCase(position);
    }
}
