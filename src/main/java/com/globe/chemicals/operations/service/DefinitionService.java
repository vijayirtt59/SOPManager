package com.globe.chemicals.operations.service;

import com.globe.chemicals.operations.entity.Definition;
import com.globe.chemicals.operations.entity.Title;
import com.globe.chemicals.operations.repository.DefinitionRepository;
import com.globe.chemicals.operations.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefinitionService {

    @Autowired
    private DefinitionRepository definitionRepository;

    @Autowired
    private TitleRepository titleRepository;

    // Get all definitions
    public List<Definition> getAllDefinitions() {
        return definitionRepository.findAll();
    }

    // Get definition by name
    public Definition getDefinitionByName(String name) {
        return definitionRepository.findByName(name);
    }

    // Create a new definition
    public Definition createDefinition(Definition definition) {
        return definitionRepository.save(definition);
    }

    // Update an existing definition
    public Definition updateDefinition(Long id, Definition updatedDefinition) {
        Optional<Definition> existingDefinition = definitionRepository.findById(id);
        if (existingDefinition.isPresent()) {
            Definition definition = existingDefinition.get();
            definition.setName(updatedDefinition.getName());
            definition.setDescription(updatedDefinition.getDescription());
            definition.setTitles(updatedDefinition.getTitles());
            return definitionRepository.save(definition);
        } else {
            return null;
        }
    }

    // Map definition to title
    public void mapDefinitionToTitle(Long definitionId, Long titleId) {
        Optional<Definition> definition = definitionRepository.findById(definitionId);
        Optional<Title> title = titleRepository.findById(titleId);

        if (definition.isPresent() && title.isPresent()) {
            definition.get().getTitles().add(title.get());
            definitionRepository.save(definition.get());
        }
    }
}
