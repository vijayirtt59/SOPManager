package com.globe.chemicals.operations.dto;

import com.globe.chemicals.operations.dto.ResponsibilityDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TitleDTO {

    private Long id;
    private String name;
    private String procedureNumber;
    private LocalDate applicationDate;
    private int substitute;
    private String revisionNo;
    private LocalDate nextRevision;
    private List<ResponsibilityDTO> responsibilities;
    private List<DefinitionDTO> definitions;
    private List<DevelopmentDTO> developments;
}
