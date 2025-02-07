package com.pfe.projetpfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pfe.projetpfe.entity.Module;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepositorie extends JpaRepository<Module,Long> {

}
