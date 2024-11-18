package com.globe.chemicals.operations.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responsibilities")
@Data
public class Responsibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position;

    @Column
    private String subPositions;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ElementCollection
    @CollectionTable(name = "responsibility_bullet_points", joinColumns = @JoinColumn(name = "responsibility_id"))
    @Column(name = "bullet_point")
    private List<String> bulletPoints;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "responsibility_title",
            joinColumns = @JoinColumn(name = "responsibility_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id")
    )
    @JsonManagedReference
    @JsonIgnore
    private List<Title> titles = new ArrayList<>();
}
