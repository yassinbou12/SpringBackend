package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.AppRole;
import com.pfe.projetpfe.entity.Role;
import com.pfe.projetpfe.entity.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByroleName(TypeRole roleName);

}
