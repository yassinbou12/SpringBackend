package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "module")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="moduleId")
    private Long moduleId;
    private String moduleName;
    private String semestre;
    @Max(9000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professeur_id", nullable = false) // la clé étrangère dans la table Module
    private Professeur professeur;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private List<Resources> listResources =new ArrayList<>();
}
