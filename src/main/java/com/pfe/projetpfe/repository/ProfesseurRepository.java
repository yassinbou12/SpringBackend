package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    Professeur findByEmail(String email);

    Optional<Professeur> findByNom(String nom);
}
