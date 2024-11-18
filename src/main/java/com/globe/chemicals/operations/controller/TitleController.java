package com.globe.chemicals.operations.controller;

import com.globe.chemicals.operations.dto.DefinitionDTO;
import com.globe.chemicals.operations.dto.DevelopmentDTO;
import com.globe.chemicals.operations.dto.ResponsibilityDTO;
import com.globe.chemicals.operations.entity.Development;
import com.globe.chemicals.operations.entity.Title;
import com.globe.chemicals.operations.dto.TitleDTO;
import com.globe.chemicals.operations.service.TitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/titles")
@Slf4j
public class TitleController {

    private static Logger logger = Logger.getLogger("TitleController");

    @Autowired
    private TitleService titleService;

    @GetMapping
    public List<Title> getAllTitles() {
        return titleService.getAllTitles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TitleDTO> getTitleById(@PathVariable Long id) {
        Optional<Title> title = titleService.getTitleById(id);
        return title.map(t -> ResponseEntity.ok(mapToDTO(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Title createTitle(@RequestBody Title title) {
        return titleService.saveTitle(title);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Title> updateTitle(@PathVariable Long id, @RequestBody Title titleDetails) {
        Optional<Title> title = titleService.getTitleById(id);
        if (title.isPresent()) {
            Title updatedTitle = title.get();
            updatedTitle.setName(titleDetails.getName());
            updatedTitle.setProcedureNumber(titleDetails.getProcedureNumber());
            updatedTitle.setApplicationDate(titleDetails.getApplicationDate());
            updatedTitle.setSubstitute(titleDetails.getSubstitute());
            updatedTitle.setRevisionNo(titleDetails.getRevisionNo());
            updatedTitle.setNextRevision(titleDetails.getNextRevision());
            titleService.saveTitle(updatedTitle);
            return ResponseEntity.ok(updatedTitle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) {
        Optional<Title> title = titleService.getTitleById(id);
        if (title.isPresent()) {
            titleService.deleteTitle(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private TitleDTO mapToDTO(Title title) {
        TitleDTO dto = new TitleDTO();
        dto.setId(title.getId());
        dto.setName(title.getName());
        dto.setProcedureNumber(title.getProcedureNumber());
        dto.setApplicationDate(title.getApplicationDate());
        dto.setSubstitute(title.getSubstitute());
        dto.setRevisionNo(title.getRevisionNo());
        dto.setNextRevision(title.getNextRevision());

        List<ResponsibilityDTO> responsibilities = title.getResponsibilities().stream()
                .map(res -> ResponsibilityDTO.builder()
                        .id(res.getId())
                        .note(res.getNote())
                        .position(res.getPosition())
                        .subPositions(res.getSubPositions())
                        .bulletPoints(res.getBulletPoints())
                        .build())
                .collect(Collectors.toList());
        dto.setResponsibilities(responsibilities);

        List<DefinitionDTO> definitions = title.getDefinitions().stream()
                .map(definition -> DefinitionDTO.builder()
                        .id(definition.getId())
                        .name(definition.getName())
                        .description(definition.getDescription())
                        .build())
                .collect(Collectors.toList());
        dto.setDefinitions(definitions);

        List<DevelopmentDTO> developments = title.getDevelopments().stream()
                .map(development -> DevelopmentDTO.builder()
                        .id(development.getId())
                        .number(development.getNumber())
                        .activity(development.getActivity())
                        .responsible(development.getResponsible())
                        .description(getDescriptions(development))
                        .build())
                .collect(Collectors.toList());
        dto.setDevelopments(developments);

        return dto;
    }

    public List<String> getDescriptions(Development development){
        if(null != development && null != development.getDescription()){
            return Arrays.asList(development.getDescription().get(0).split("\n"));
        }
        return Collections.emptyList();
    }
}
