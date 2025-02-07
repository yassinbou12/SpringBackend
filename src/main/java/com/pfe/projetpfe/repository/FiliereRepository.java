package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {

}
