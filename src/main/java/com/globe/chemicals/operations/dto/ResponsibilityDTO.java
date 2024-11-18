package com.globe.chemicals.operations.dto;

import com.globe.chemicals.operations.entity.Title;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponsibilityDTO {

    private Long id;

    private String position;

    private String subPositions;

    private String note;

    private List<String> bulletPoints;
}
