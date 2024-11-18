package com.globe.chemicals.operations.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DevelopmentDTO {
    private Long id;
    private String number;
    private String activity;
    private String responsible;
    private List<String> description;
}
