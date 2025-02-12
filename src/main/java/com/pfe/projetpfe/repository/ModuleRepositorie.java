package com.pfe.projetpfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pfe.projetpfe.entity.Module;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepositorie extends JpaRepository<Module,Long> {
    Optional<Module> findByModuleName(String moduleName);
    List<Module> findByProfesseurId(Long professeurId);
}
