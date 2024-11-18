package com.globe.chemicals.operations.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Developments")
@Data
public class Development {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(unique = true)
    private String activity;

    private String responsible;

    @ElementCollection
    @CollectionTable(name = "development_descriptions", joinColumns = @JoinColumn(name = "development_id"))
    @Column(name = "description", length=1000)
    private List<String> description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "development_title",
            joinColumns = @JoinColumn(name = "development_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id")
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Title> titles = new ArrayList<>();
}
