package com.globe.chemicals.operations.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "titles")
@Data
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String procedureNumber;
    private LocalDate applicationDate;
    private int substitute;
    private String revisionNo;
    private LocalDate nextRevision;

    @ManyToMany(mappedBy = "titles")
    @JsonBackReference
    private List<Responsibility> responsibilities = new ArrayList<>();

    @ManyToMany(mappedBy = "titles")
    @JsonBackReference
    private List<Definition> definitions = new ArrayList<>();

    @ManyToMany(mappedBy = "titles")
    @JsonBackReference
    private List<Development> developments = new ArrayList<>();
}
