package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourcesRepository extends JpaRepository<Resources,Long> {
    List<Resources> findAllByProfesseurId(Long professeurId);
}
