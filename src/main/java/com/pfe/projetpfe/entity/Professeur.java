package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "professeur")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Professeur extends Personne {

    @OneToMany(mappedBy = "professeur")
    private Collection<Module> modules;



}
