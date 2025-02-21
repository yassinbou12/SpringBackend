package com.pfe.projetpfe.service;

import com.pfe.projetpfe.Dto.*;
import com.pfe.projetpfe.entity.Professeur;
import com.pfe.projetpfe.entity.Resources;

import java.util.List;

public interface AdminService {
    String  encryptPassword(String password);
    List<ProfDto> getAllProfs();
    ProfDto getProfByEmail(String email);
    AdminDto getAdminById(Long id);
    ProfDto addNewProf(RegistrDto professeurRegistrDto);
    ProfDto updateProf(Professeur professeur);
    ReturnFiliereDto addNewFiliere(AddFiliereDto addFiliereDto);
    ReturnFiliereDto updateFiliere(ReturnFiliereDto returnFiliereDto);
    List<ReturnFiliereDto> getAllFilieres();
}
