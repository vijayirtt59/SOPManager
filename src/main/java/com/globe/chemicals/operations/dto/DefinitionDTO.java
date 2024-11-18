package com.globe.chemicals.operations.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefinitionDTO {
    private Long id;
    private String name;
    private String description;
}
