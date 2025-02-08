package com.pfe.projetpfe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "professeur")
@Data
@NoArgsConstructor
public class Professeur extends Personne {

    @OneToMany(mappedBy = "professeur")
    private Collection<Module> modules;



}
