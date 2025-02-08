package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierRepository extends JpaRepository<Resources,Long> {
}