package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professeur_id", nullable = false) // la clé étrangère dans la table Module
    private Professeur professeur;
}
