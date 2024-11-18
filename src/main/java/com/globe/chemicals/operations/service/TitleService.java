package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.Title;
import com.globe.chemicals.operations.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitleService {

    @Autowired
    private TitleRepository titleRepository;

    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    public Optional<Title> getTitleById(Long id) {
        return titleRepository.findByIdWithResponsibilities(id);
    }

    public Title saveTitle(Title title) {
        return titleRepository.save(title);
    }

    public void deleteTitle(Long id) {
        titleRepository.deleteById(id);
    }
}
