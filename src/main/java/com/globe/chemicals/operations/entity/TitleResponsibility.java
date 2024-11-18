package com.globe.chemicals.operations.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "title_responsibilities")
@Data
public class TitleResponsibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;

    @ManyToOne
    @JoinColumn(name = "responsibility_id", nullable = false)
    private Responsibility responsibility;
}
