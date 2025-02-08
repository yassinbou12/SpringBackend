package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.entity.AppRole;
import com.pfe.projetpfe.entity.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface RoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByroleName(TypeRole roleName);
}
