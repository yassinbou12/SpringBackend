package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "filiere")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFiliere;

    private String nomFiliere;

    private String annneeFiliere;

    private String semestreFiliere;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Module> moduleList;
}
