package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.AdminDto;
import com.pfe.projetpfe.Dto.ProfDto;
import com.pfe.projetpfe.Dto.RegistrDto;
import com.pfe.projetpfe.entity.Professeur;

import java.util.List;

public interface AdminService {
    String  encryptPassword(String password);
    List<ProfDto> getAllProfs();
    ProfDto getProfByName(String name);
    ProfDto getProfByEmail(String email);
    AdminDto getAdminById(Long id);
}
