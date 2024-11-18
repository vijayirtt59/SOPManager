package com.globe.chemicals.operations.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Definitions")
@Data
public class Definition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "definition_title",
            joinColumns = @JoinColumn(name = "definition_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id")
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Title> titles = new ArrayList<>();
}
