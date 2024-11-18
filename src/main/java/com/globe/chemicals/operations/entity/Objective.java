package com.globe.chemicals.operations.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;


import java.util.List;

@Entity
@Table(name = "Objectives")
@Data
public class Objective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String titleName;

    @Lob
    private String paragraph;

    @ElementCollection
    @CollectionTable(name = "objective_bullet_points", joinColumns = @JoinColumn(name = "objectives_id"))
    @Column(name = "bullet_point")
    private List<String> bulletPoints;

}
