package com.pfe.projetpfe.repository;

import com.pfe.projetpfe.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    AdminRepository findByEmail(String email);
}
